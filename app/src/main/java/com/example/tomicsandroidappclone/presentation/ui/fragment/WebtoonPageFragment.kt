package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.databinding.FragmentWebtoonPageBinding
import com.example.tomicsandroidappclone.domain.di.EasyAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.PagingAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.WebtoonFragmentViewModel
import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WebtoonPageFragment : Fragment() {

    @EasyAdapter
    @Inject
    lateinit var myEasyAdapter: MyEasyAdapter
    private val viewModel: WebtoonFragmentViewModel by lazy { ViewModelProvider(this)[WebtoonFragmentViewModel::class.java] }
    private lateinit var binding: FragmentWebtoonPageBinding
    private var tabItems: Array<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabItems = it.getStringArray(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebtoonPageBinding.inflate(layoutInflater, container, false)

        /*viewModel.webtoonsInfo.observe(viewLifecycleOwner) {
            setAdapter(it)
        }*/

        binding.vpWebtoonPage.apply {
            adapter = PagingAdapter()
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 7 // view pager 양 옆 page 미리 생성
        }

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest {
                PagingAdapter().submitData(it)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
        private const val ARG_PARAM1 = "tabItems"

        @JvmStatic
        fun newInstance(tabItems: Array<String>) =
            WebtoonPageFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(ARG_PARAM1, tabItems)
                }
            }
    }

    private fun init() {
        setTab()
        setRadioGroup()
    }

    private fun setTab() {
        tabItems?.let {
            if (tabItems!![0] != "전체") {
                myEasyAdapter.addTabs(binding.tlFreeWebtoonFragment, it)
                binding.openSearchViewToolbarContainer.layoutParams.height = 135
            } else {
                myEasyAdapter.addTabs(binding.tlFreeWebtoonFragment, it, true)
            }
        }
        Log.d("TAG", "setTab: $tabItems")
    }

    private fun setRadioGroup() {
        tabItems?.let {
            if (tabItems!![0] != "전체") {
                binding.rgMain.visibility = RadioGroup.GONE
            } else {
                binding.rgMain.apply {
                    check(0)
                    setOnCheckedChangeListener { radioGroup, _ ->
                        radioGroup.clearAnimation()
                        radioGroup.jumpDrawablesToCurrentState()
                    }
                }
            }
        }
    }

    /*private fun setAdapter(webtoons: ArrayList<Webtoon>) {
        val int = if (tabItems!![0] != "뜨는 한 컷") {
            1
        } else {
            0
        }

        binding.vpWebtoonPage.apply {
            adapter = ViewPagerDefaultToonAdapter(webtoons, int, tabItems!!.size)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 7 // view pager 양 옆 page 미리 생성
        }
    }*/
}