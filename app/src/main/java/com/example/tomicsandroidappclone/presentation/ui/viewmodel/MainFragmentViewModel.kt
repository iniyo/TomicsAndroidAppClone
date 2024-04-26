package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
) : ViewModel() {
    // MutableLiveData - 읽고 쓰기 가능
    // LivaData - 읽기만 가능
    private val _toonResponse = MutableLiveData<ToonResponse>() // webtoon 전체 정보
    val toonResponse: LiveData<ToonResponse> = _toonResponse

    private val _webtoonsInfo = MutableLiveData<ArrayList<Webtoon>>()
    val webtoonsInfo: LiveData<ArrayList<Webtoon>> = _webtoonsInfo
    fun fetchWebtoons() {
        // 코루틴 - 멀티 스레드와 비슷함. 차이점 있음.
        // 1.
        Log.d("TAG", "fetchWebtoons 실행 : ")
        viewModelScope.launch {
            // !!!!! kakao를 제외한 naver, kakaoPage에는 접근이 불가. URl 권한 문제 때문으로 추정 나중에 정리하면서 문제 알아보기
            // api를 보니 비동기 접속에 대해 이미지를 받아오는 것이 막혀있는 것으로 확인.
            val toonResponseResult = webtoonRepository.getWebtoon(1, 3, "kakao", "sun")
            toonResponseResult.let {
                Log.d("TAG", "fetchwebtoons 코루틴 실행: toonResponseResult.let")
                // default kakao, mon
                _webtoonsInfo.value = webtoonRepository.getDayByWebtoons("kakao", "mon")
                _toonResponse.value = toonResponseResult
                Log.d("TAG", "fetchwebtoons 코루틴 데이터 체크:" + _toonResponse.value!!.webtoons[0].title)
                Log.d("TAG", "fetchwebtoons 코루틴 데이터 체크:" + _webtoonsInfo.value!![0].title)
            }
        }
    }

    fun getWebtoons(): ArrayList<Webtoon> {
        return _webtoonsInfo.value!!
    }

}
