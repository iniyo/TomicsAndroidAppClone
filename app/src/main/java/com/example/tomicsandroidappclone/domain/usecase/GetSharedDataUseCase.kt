package com.example.tomicsandroidappclone.domain.usecase

import android.util.Log
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import java.util.Calendar

class GetSharedDataUseCase(
    private val webtoonRepository: WebtoonRepository
) {
    suspend fun getTodayWebtoonData(): ArrayList<Webtoon>{
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
            else -> {"mon"}
        }
        Log.d("TAG", dayOfWeekString)
        return webtoonRepository.getDayByWebtoons("kakao", dayOfWeekString)
    }
}