package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.FragmentEndedWebtoonPageBinding
import com.example.tomicsandroidappclone.domain.repository.AdapterRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EndedWebtoonPageFragment : Fragment() {

    @Inject lateinit var adapterRepository: AdapterRepository
    private lateinit var binding: FragmentEndedWebtoonPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndedWebtoonPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    companion object {
        fun newInstance() = EndedWebtoonPageFragment()
    }
    private fun init() {
        setAdapter()
    }
    private fun setAdapter() {
        val fragmentArray: Array<String>?
        fragmentArray = resources.getStringArray(R.array.free_webtoon_tab_items)
        adapterRepository.addTabs(binding.tlEndedWebtoon, fragmentArray)
    }
}