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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.databinding.FragmentWebtoonPageBinding
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerDefaultToonAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerTabAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.handler.MyEasyTapControllHandler
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WebtoonPageFragment : Fragment() {

    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }
    private lateinit var binding: FragmentWebtoonPageBinding
    private var tabItems: Array<String>? = null
    private lateinit var esayController: MyEasyTapControllHandler
    private val controllerHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0, 1, 2, 3, 4 -> updateUIWithMessage(msg.obj as String)
            }
        }
    }

    private fun updateUIWithMessage(message: String) {
        // 메시지에 따라 UI 업데이트 로직 구현
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
        setHandleListener()
        setAdapter()
        setRadioGroup()
    }

    private fun setHandleListener() {
        Log.d("TAG", "setHandleListener 실행: ")
        val runnable = Runnable {
            run {
                userSelected()
            }
        }
        controllerHandler.post(runnable)
        controllerHandler.hasMessages(1) {
            Log.d("TAG", "hasMessages 실행: ")
        }

    }

    private fun setTab() {
        binding.apply {
            esayController = MyEasyTapControllHandler(tlFreeWebtoonFragment)
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
        val int = if ( esayController.getTitleTabText() != "뜨는한컷") {
            1
        } else {
            2
        }

        val aadapter = ViewPagerTabAdapter(int)
        binding.vpWebtoonPage.apply {
            adapter = ViewPagerDefaultToonAdapter(int, tabItems!!.size, aadapter)
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