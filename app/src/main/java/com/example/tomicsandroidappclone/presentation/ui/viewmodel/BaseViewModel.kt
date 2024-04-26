package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
): ViewModel() {
    // not yet..
}