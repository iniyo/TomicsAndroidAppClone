package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.FragmentHotWebtoonPageBinding
import com.example.tomicsandroidappclone.domain.repository.AdapterRepository
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HotWebtoonPageFragment : Fragment() {

    @Inject lateinit var adapterRepository: AdapterRepository
    private lateinit var binding: FragmentHotWebtoonPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotWebtoonPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    companion object {
        fun newInstance() = HotWebtoonPageFragment()
    }

    private fun init() {
        setAdapter()
    }
    private fun setAdapter() {
        val fragmentArray: Array<String>?
        fragmentArray = resources.getStringArray(R.array.hot_webtoon_items)
        adapterRepository.addTabs(binding.tlHotWebtoon, fragmentArray)
    }
}