package com.example.tomicsandroidappclone.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import javax.inject.Inject

class WebtoonInfo @Inject constructor(private val webtoonRepository: WebtoonRepository){
    suspend fun getWebtoos(_webtoonInfo: MutableLiveData<ToonResponse>, webtoonInfo: LiveData<ToonResponse>){
        val webtoonResult = webtoonRepository.getWebtoon(1, 3, "kakao", "sun")
        //Log.d("tag", webtoonResult.webtoons.toString())
        _webtoonInfo.value = webtoonResult
    }
}