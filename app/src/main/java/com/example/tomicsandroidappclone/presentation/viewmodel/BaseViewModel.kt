package com.example.tomicsandroidappclone.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.data.repository.WebtoonRepositoryImpl
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.domain.repository.WebtoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
): ViewModel() {
    val webtoonInfo = MutableLiveData<ToonResponse>() // webtoon 전체 정보
    val webtoonsAllInfo = MutableLiveData<ToonResponse>()
    val webtoons = MutableLiveData<List<Webtoon>>()
    fun fetchWebtoons() {
        viewModelScope.launch {
            val webtoonResult = webtoonRepository.getWebtoon(1, 3, "naver", "sun")

            webtoonInfo.value = webtoonResult

        }
    }
}