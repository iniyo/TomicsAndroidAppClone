package com.example.tomicsandroidappclone.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomicsandroidappclone.R

class TopHunderedFragment : Fragment() {


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
        return inflater.inflate(R.layout.fragment_top_hundered, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TopHunderedFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}