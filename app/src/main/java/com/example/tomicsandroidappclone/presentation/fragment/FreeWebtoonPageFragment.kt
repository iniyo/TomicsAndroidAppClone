package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.R.array
import com.example.tomicsandroidappclone.databinding.FragmentFreeWebtoonPageBinding
import com.example.tomicsandroidappclone.domain.repository.AdapterRepository
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FreeWebtoonPageFragment : Fragment() {
    @Inject lateinit var adapterRepository: AdapterRepository
    private lateinit var binding: FragmentFreeWebtoonPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreeWebtoonPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
        fun newInstance() = FreeWebtoonPageFragment()
    }

    private fun init() {
        setAdapter()
        setRadioGroup()
    }
    private fun setAdapter() {
        val fragmentArray: Array<String>?
        fragmentArray = resources.getStringArray(array.free_webtoon_tab_items)
        adapterRepository.addTabs(binding.tlFreeWebtoonFragment, fragmentArray)
    }
    private fun setRadioGroup() {
        binding.rgMain.setOnCheckedChangeListener{ radioGroup, _ ->
            radioGroup.clearAnimation()
            radioGroup.jumpDrawablesToCurrentState()
        }
    }
}