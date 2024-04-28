package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.domain.usecase.GetKakaoWebtoonByDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getKakaoWebtoonByDayUseCase: GetKakaoWebtoonByDayUseCase
) : ViewModel() {
    // MutableLiveData - 읽고 쓰기 가능
    // LivaData - 읽기만 가능
    private val _webtoonsInfo = MutableLiveData<ArrayList<Webtoon>>()
    val webtoonsInfo: LiveData<ArrayList<Webtoon>> = _webtoonsInfo

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _webtoonsInfo.postValue(getKakaoWebtoonByDayUseCase())
            } catch (e: Exception) {
                Log.e("TAG", "MainFragmentViewModel-fetchData: $e")
            }
        }
    }
}
