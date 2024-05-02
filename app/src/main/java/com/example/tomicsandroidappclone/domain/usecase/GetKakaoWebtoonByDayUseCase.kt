package com.example.tomicsandroidappclone.domain.usecase

import android.util.Log
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.model.Webtoon
import java.util.Calendar

class GetKakaoWebtoonByDayUseCase(
    private val webtoonRepository: WebtoonRepository
) {
    private lateinit var webtoon: ArrayList<Webtoon>

    // 유스케이스는 비즈니스 로직을 제공해야 하는데, "현실 문제에 대한 의사결정을 하는 가"를 생각해야 한다.
    // 오늘의 날짜를 파악해서 해당 날짜에 맞는 웹툰을 표시한다.
    private suspend fun getTodayWebtoonData() {
        val calendar: Calendar = Calendar.getInstance()
        val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)

        val dayOfWeekString = when (dayOfWeek) {
            Calendar.SUNDAY -> "sun"
            Calendar.MONDAY -> "mon"
            Calendar.TUESDAY -> "tue"
            Calendar.WEDNESDAY -> "wed"
            Calendar.THURSDAY -> "thu"
            Calendar.FRIDAY -> "fri"
            Calendar.SATURDAY -> "sat"
            else -> "null"
        }
        Log.d("TAG", dayOfWeekString)
        webtoon = webtoonRepository.getDayByWebtoons("kakao", dayOfWeekString)
    }

    // invoke - operator관례 함수
    suspend operator fun invoke(): ArrayList<Webtoon> {
        getTodayWebtoonData()
        return webtoon
    }
}