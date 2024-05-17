package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.databinding.FragmentWebtoonPageBinding
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerDefaultToonAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerTabAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.handler.MyEasyTapControllHandler
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WebtoonPageFragment : Fragment() {

    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }
    private lateinit var binding: FragmentWebtoonPageBinding
    private lateinit var titleTabText: String
    private var detailTabText: String? = null
    private lateinit var mEsayController: MyEasyTapControllHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleTabText = it.getString(ARG_PARAM1).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebtoonPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        adjustImageHeight()
    }
    private fun adjustImageHeight() {
        binding.apply {
            collapsingToolbar.viewTreeObserver.addOnGlobalLayoutListener {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                val screenWidth = displayMetrics.widthPixels
                val aspectRatio = 16 / 3f // 이미지의 종횡비(예: 16:9)

                val imageHeight = (screenWidth / aspectRatio).toInt()
                ivAdvertisement.layoutParams.height = imageHeight
                ivAdvertisement.requestLayout()
            }
        }
    }
    companion object {
        private const val ARG_PARAM1 = "tab"

        @JvmStatic
        fun newInstance(tab: String) =
            WebtoonPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, tab)
                }
            }
    }

    private fun init() {
        setTab()
        setRadioGroup()
    }

    private fun setTab() {
        binding.apply {
            mEsayController = MyEasyTapControllHandler(tlFreeWebtoonFragment)
            val tabItems = context?.let { MyStringMapper().getTitleTabItemArray(titleTabText, it) }
            if (titleTabText == "나만무료") tabItems?.let { mEsayController.addTabs(it, true) }
            else tabItems?.let { mEsayController.addTabs(it) }
            userSelected()

            tlFreeWebtoonFragment.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        val position = it.position
                        try {
                            Log.d("TAG", "handleTabSelection titleTabText: $titleTabText")
                            handleTabSelection(position, tlFreeWebtoonFragment.tabCount, tab)
                        } catch (e: Exception) {
                            Log.e("TAG", "onTabSelected: ${e.message}")
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.d("TAG", "onTabUnselected")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.d("TAG", "onTabReselected")
                }
            })
            setAdapter()
            TabLayoutMediator(tlFreeWebtoonFragment, vpWebtoonPage) { tab, position ->
                tab.text = tabItems?.get(position) // 탭에 표시할 텍스트 설정
            }.attach()
        }
    }

    private fun setRadioGroup() {
        Log.d("TAG", "setRadioGroup:$detailTabText ")
        binding.apply {
            if (detailTabText != "전체") {
                rgMain.visibility = RadioGroup.GONE
            } else {
                vSpace.visibility = View.GONE
                rgMain.apply {
                    check(0)
                    setOnCheckedChangeListener { radioGroup, _ ->

                    }
                }
            }
        }
    }

    private fun userSelected() {
        Log.d("TAG", "userSelected: $titleTabText")
        if (titleTabText == "연재") {
            Log.d("TAG", "userSelected: ${detailTabText?.let { detailTabText }}")
            detailTabText?.let { viewModel.getSelectDayWebtoon(it) }
        }
    }

    private fun setAdapter() {
        val int = if (titleTabText != "뜨는한컷") {
            1
        } else {
            2
        }

        val tabAdapter = ViewPagerTabAdapter(int)
        val tabItems = context?.let { MyStringMapper().getTitleTabItemArray(titleTabText, it) }
        binding.vpWebtoonPage.apply {
            adapter = ViewPagerDefaultToonAdapter(int, tabItems!!.size, tabAdapter)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1 // view pager 양 옆 page 미리 생성
        }
        lifecycleScope.launch {
            // repeatOnLifecycle은 라이프사이클 상태에 따라 코루틴을 관리
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingData.collectLatest { pagingDataFlow ->
                    pagingDataFlow?.let {
                        // collectLatest를 사용하여 최신 데이터만 처리
                        it.collectLatest { pagingData ->
                            tabAdapter.submitData(pagingData)
                        }
                    }
                }
            }
        }
    }

    private fun handleTabSelection(position: Int, tabCount: Int, tab: TabLayout.Tab) {
        if (position !in 0 until tabCount) {
            throw NullPointerException("Invalid tab position")
        }
        if (titleTabText == "연재"){
            detailTabText = MyStringMapper().getDayForKor2Eng(tab.text.toString())
            Log.d("TAG", "handleTabSelection 연재: $detailTabText")
            userSelected()
        }else {
            detailTabText = tab.text.toString()
            Log.d("TAG", "handleTabSelection detailTabText: $detailTabText")
            userSelected()
        }
    }
}