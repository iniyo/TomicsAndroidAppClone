package com.example.tomicsandroidappclone.presentation.util.easyutil

import java.util.Calendar

object MyCalendar {
    fun toDay() : String {
        return invoke()
    }
    operator fun invoke() : String {
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
        return dayOfWeekString
    }
}