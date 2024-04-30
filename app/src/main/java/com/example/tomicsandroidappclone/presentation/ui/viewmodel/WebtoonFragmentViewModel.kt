package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tomicsandroidappclone.data.repository.PagingRepository
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WebtoonFragmentViewModel @Inject constructor(
    private val pRepsitory: PagingRepository,
    private val webtoonRepository: WebtoonRepository
) : ViewModel() {
    val pagingData = pRepsitory.getPagingData().cachedIn(viewModelScope)

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
            val toonResponseResult = webtoonRepository.getWebtoon(1, 3, "kakao", "sun")
            toonResponseResult.let {
                // default kakao, mon
                _webtoonsInfo.value = webtoonRepository.getDayByWebtoons("kakao", "mon")
                _toonResponse.value = toonResponseResult
            }
        }
    }

    fun getWebtoons(): ArrayList<Webtoon> {
        return _webtoonsInfo.value!!
    }
}
