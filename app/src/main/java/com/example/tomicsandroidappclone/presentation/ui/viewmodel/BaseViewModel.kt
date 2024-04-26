package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
): ViewModel() {

    private val _webtoonInfo = MutableLiveData<ToonResponse>() // webtoon 전체 정보
    val webtoonInfo: LiveData<ToonResponse> = _webtoonInfo
    fun fetchWebtoons() {
        // 코루틴 - 멀티 스레드와 비슷함. 차이점 있음.
        // 1.
        viewModelScope.launch {
            val webtoonResult = webtoonRepository.getWebtoon(1, 3, "naver", "sun")
            //Log.d("tag", webtoonResult.webtoons.toString())
            _webtoonInfo.value = webtoonResult
        }
    }
}