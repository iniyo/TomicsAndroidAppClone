package com.example.tomicsandroidappclone.domain.usecase

import android.util.Log
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import java.util.Calendar

class GetKakaoWebtoonByDayUseCase(
    private val webtoonRepository: WebtoonRepository
) {
    private lateinit var webtoon: ArrayList<Webtoon>

    suspend fun getTodayWebtoonData() {
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
            else -> {"null"}
        }
        Log.d("TAG", dayOfWeekString)
        webtoon = webtoonRepository.getDayByWebtoons("kakao", dayOfWeekString)
    }

    // invoke - operator관례 함수
    suspend operator fun invoke() : ArrayList<Webtoon> {
        getTodayWebtoonData()
        return webtoon
    }
}