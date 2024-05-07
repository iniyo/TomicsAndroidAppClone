package com.example.tomicsandroidappclone.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyCalendar
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetToonByDayUseCase @Inject constructor(private val webtoonRepository: WebtoonRepository) {
    private lateinit var webtoon: ArrayList<Webtoon>

    // 유스케이스는 비즈니스 로직을 제공해야 하는데, "현실 문제에 대한 의사결정을 하는 가"를 생각해야 한다.
    // 오늘의 날짜를 파악해서 해당 날짜에 맞는 웹툰을 표시한다.
    private suspend fun getTodayWebtoonData() {
        webtoon = webtoonRepository.getDayByWebtoons("kakao", MyCalendar().invoke())
    }

    fun getAllWebtoon(): Flow<PagingData<Webtoon>> {
        return webtoonRepository.getAllToonPagingData()
    }
    fun getUserSelectDayToonData(selectDay: String) : Flow<PagingData<Webtoon>> {
        Log.d("TAG", "usecase: $selectDay ")
        return webtoonRepository.getDayByWebtoonsForPaging(selectDay)
    }
    // invoke - operator관례 함수
    suspend operator fun invoke(): ArrayList<Webtoon> {
        getTodayWebtoonData()
        return webtoon
    }
}