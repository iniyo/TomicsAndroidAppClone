package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.domain.usecase.GetSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getSharedDataUseCase: GetSharedDataUseCase
) : ViewModel() {
    // MutableLiveData - 읽고 쓰기 가능
    // LivaData - 읽기만 가능
    private val _webtoonsInfo = MutableLiveData<ArrayList<Webtoon>>()
    val webtoonsInfo: LiveData<ArrayList<Webtoon>> = _webtoonsInfo
/*
    private val _toonResponse = MutableLiveData<ToonResponse>() // webtoon 전체 정보
    val toonResponse: LiveData<ToonResponse> = _toonResponse
*/
    fun fetchData() = viewModelScope.launch{
        _webtoonsInfo.postValue(getSharedDataUseCase.getTodayWebtoonData())
    }
    fun getWebtoons(): ArrayList<Webtoon> {
        // UI 스레드에서 LiveData 값을 안전하게 가져옴
        return webtoonsInfo.value!!
    }
}
