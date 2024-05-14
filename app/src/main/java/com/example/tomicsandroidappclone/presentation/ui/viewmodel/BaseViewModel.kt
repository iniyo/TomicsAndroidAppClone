package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.domain.usecase.GetToonByDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val getToonByDayUseCase: GetToonByDayUseCase,
) : ViewModel() {
    private val _pagingData = MutableStateFlow<Flow<PagingData<Webtoon>>?>(null)
    val pagingData: StateFlow<Flow<PagingData<Webtoon>>?> = _pagingData.asStateFlow()

    private val _webtoonsInfo = MutableLiveData<ArrayList<Webtoon>>()
    val webtoonsInfo: LiveData<ArrayList<Webtoon>> = _webtoonsInfo

    fun getSelectDayWebtoon(today: String) {
        viewModelScope.launch {
            try {
                val webtoonsFlow = getToonByDayUseCase.getUserSelectDayToonData(today).cachedIn(viewModelScope)
                withContext(Dispatchers.Main) {
                    _pagingData.value = webtoonsFlow
                }
            } catch (e: Exception) {
                Log.e("TAG", "getSelectDayWebtoon error: ${e.message}")
            }
        }
    }
    init {
        Log.d("TAG", "BaseViewModel - init ")
        fetchWebtoons()
    }
    private var cachedPagingData: Flow<PagingData<Webtoon>>? = null
    fun loadWebtoonData(today: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cachedPagingData = getToonByDayUseCase.getUserSelectDayToonData(today).cachedIn(viewModelScope)
            } catch (e: Exception) {
                Log.e("TAG", "loadWebtoonData error: ${e.message}")
            }
        }
    }

    fun applyLoadedData() {
        cachedPagingData?.let {
            viewModelScope.launch(Dispatchers.Main) {
                _pagingData.value = it
            }
        }
    }
    private fun fetchWebtoons() {
        // 코루틴 - 멀티 스레드와 비슷함. 차이점 있음. (둘 다 동시성 프로그래밍)
        // 1. 스레드와 달리 특정 스레드에 종속되지 않음. 객체로 존재함.
        // 2. 스레드는 독립적인 stack 메모리 영역을 가지는 반면 코루틴은 object로 힙 영역에 할당 됨.
        // 3. 같은 스레드에서 사용되는 각 코루틴은 동시에 수행될 수 있다. 즉 하나의 스레드는 여러 코루틴을 수행할 수 있다.
        // 4. 코루틴은 스레드를 더 잘개 쪼개어 사용하기 위한 방법으로 context switching을 걱정할 필요가 없다. (Light-weight Thread)

        Log.d("TAG", "fetchWebtoons 실행 : ")
        viewModelScope.launch {
            // !!!!! kakao를 제외한 naver, kakaoPage에는 접근이 불가. URl 권한 문제 때문으로 추정 나중에 정리하면서 문제 알아보기
            val toonResponseResult = getToonByDayUseCase
            withContext(Dispatchers.Main) {
                _webtoonsInfo.value = toonResponseResult()
            }
        }
    }
}