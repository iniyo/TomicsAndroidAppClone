package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import retrofit2.Retrofit

class MainPageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainPageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}