package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
) : ViewModel() {
    // not yet..
}