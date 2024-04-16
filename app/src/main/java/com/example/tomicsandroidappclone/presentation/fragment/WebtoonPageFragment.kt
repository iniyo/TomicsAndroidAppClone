package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.tomicsandroidappclone.databinding.FragmentWebtoonPageBinding
import com.example.tomicsandroidappclone.domain.repository.AdapterRepository
import com.example.tomicsandroidappclone.presentation.adapter.PopularityToonAdapter
import com.example.tomicsandroidappclone.presentation.viewmodel.fragment_view_model.WebtoonFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM1 = "tabItems"

@AndroidEntryPoint
class WebtoonPageFragment : Fragment() {

    @Inject
    lateinit var adapterRepository: AdapterRepository
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

        viewModel.fetchWebtoons()

        binding.run {
            Log.d("TAG", "binding run")
            viewModel.webtoonsInfo.observe(viewLifecycleOwner) { data ->
                Log.d("TAG", "binding observer1" + data[0].title)
                setAdapter()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
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
                adapterRepository.addTabs(binding.tlFreeWebtoonFragment, it)
                binding.openSearchViewToolbarContainer.layoutParams.height = 135
            } else {
                adapterRepository.addTabs(binding.tlFreeWebtoonFragment, it, true)
            }
        }
        Log.d("TAG", "setTab: $tabItems")
    }

    private fun setRadioGroup() {
        tabItems?.let {
            if (tabItems!![0] != "전체") {
                binding.rgMain.visibility = RadioGroup.GONE
            } else {
                binding.rgMain.setOnCheckedChangeListener { radioGroup, _ ->
                    radioGroup.clearAnimation()
                    radioGroup.jumpDrawablesToCurrentState()
                }
            }
        }
    }

    private fun setAdapter() {
        val webtoons = viewModel.getWebtoons()
        var int: Int
        if (tabItems!![0] != "뜨는 한 컷") {
            int = 0
        } else {
            int = 1
        }

        binding.rvWebtoonPage.apply {
            adapter = PopularityToonAdapter(webtoons, int)
            layoutManager = LinearLayoutManager(
                binding.rvWebtoonPage.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvWebtoonPage)
            setHasFixedSize(true)
        }

    }

}