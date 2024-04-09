package com.example.tomicsandroidappclone.presentation.viewmodel.fragment_view_model

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val webtoonRepository: WebtoonRepository
) : ViewModel() {
    // MutableLiveData 읽고 쓰기 가능
    // LivaData 읽기만 가능
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
            toonResponseResult.let{
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

        val sortedWebtoons = _webtoonsInfo.value!!.sortedByDescending { it.rank }
        return sortedWebtoons as ArrayList<Webtoon>
    }

    /*fun setWebtton(tagList:Array<String>) {
        val randomIndex = Random.nextInt(tagList.size)
        val randomItem = tagList[randomIndex]
        for (i in 0 until _webtoonsInfo.value!!.size) {
            _webtoonsInfo.value!![i].rank = i
            _webtoonsInfo.value!![i].tagList.tag1 = randomItem
            _webtoonsInfo.value!![i].tagList.tag2 = randomItem
            _webtoonsInfo.value!![i].tagList.tag3 = randomItem
        }
        getWebtoons()

    }*/
    fun getAllWebtoons(): List<Webtoon> {
        val webtoons = mutableListOf<Webtoon>()

        // let을 통해 null이 아니면 람다 함수 실행 아니라면 오른쪽 피 연산자 반환
        _toonResponse.value?.webtoons?.let { toonList ->
            webtoons.addAll(toonList)
        }
        return webtoons
    }
    fun getSeviceAndUpdateDayByWebtoon(service: String, updateDay: String) {
        viewModelScope.launch {
            _webtoonsInfo.value = webtoonRepository.getDayByWebtoons(service, updateDay)
        }
    }
}
