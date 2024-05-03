package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
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
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerTabAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyTapController
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WebtoonPageFragment : Fragment() {

    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }
    private lateinit var binding: FragmentWebtoonPageBinding
    private var tabItems: Array<String>? = null
    private lateinit var esayController: MyEasyTapController
    private val controllerHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                // 예시 메시지 유형에 따른 처리
                0 -> userSelected()
                1 -> userSelected()
                2 -> userSelected()
                3 -> userSelected()
                4 -> userSelected()
            }
        }
    }
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
        /*setHandleListener()*/
        setAdapter()
        setRadioGroup()
    }

    /*private fun setHandleListener() {
        Log.d("TAG", "setHandleListener 실행: ")
        handler.post {
            val message = handler.obtainMessage()
            message.what = 1
            handler.sendMessage(message)
            userSelected()
            *//*handler.removeMessages(1)*//*
        }

        Log.d("TAG", "setHandleListener 받음.: ")
    }*/

    private fun setTab() {
        binding.apply {
            esayController = MyEasyTapController(tlFreeWebtoonFragment)
            tabItems?.let {
                if (tabItems!![0] != "전체") {
                    esayController.addTabs(it)
                    openSearchViewToolbarContainer.layoutParams.height = 135
                } else {
                    esayController.addTabs( it, true)
                }
            }
        }
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

    private fun userSelected()
    {
        val detailTabText = esayController.getDetailTabText()
        val titleTabText = esayController.getTitleTabText()
        Log.d("TAG", "userSelected: $titleTabText")
        if (titleTabText=="연재"){
            Log.d("TAG", "userSelected: ${MyStringMapper().getDayForKor2Eng(detailTabText)}")
            viewModel.getSelectDayWebtoon(detailTabText)
        }
    }

    private fun setAdapter() {
        val int = if ( esayController.getTitleTabText() != "뜨는 한 컷") {
            1
        } else {
            2
        }
        val aadapter = ViewPagerTabAdapter(int)
        binding.vpWebtoonPage.apply {
            adapter = aadapter/*ViewPagerDefaultToonAdapter(int, tabItems!!.size)*/
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1 // view pager 양 옆 page 미리 생성
        }

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest {
                Log.d("TAG", "이거 ")
                aadapter.submitData(it)
            }
        }
    }
}