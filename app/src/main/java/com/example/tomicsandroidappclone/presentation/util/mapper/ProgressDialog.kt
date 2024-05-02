package com.example.tomicsandroidappclone.presentation.util.mapper
import android.R
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.tomicsandroidappclone.databinding.DialogProgressBinding


class ProgressDialog(context: Context?) : Dialog(context!!) {
    private var binding: DialogProgressBinding
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}