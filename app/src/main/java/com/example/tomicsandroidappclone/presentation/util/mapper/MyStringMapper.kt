package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.Context
import com.example.tomicsandroidappclone.R

class MyStringMapper {
    fun getTitleTabItemArray(tabItem: String, context: Context): Array<String>? {
        val arrayString = when (tabItem) {
            "나만무료" -> context.resources.getStringArray(R.array.free_webtoon_tab_items)
            "연재" -> context.resources.getStringArray(R.array.serialize_tab_items)
            "TOP100" -> context.resources.getStringArray(R.array.top_webtoon_items)
            "완결" -> context.resources.getStringArray(R.array.ended_webtoon_items)
            "뜨는한컷" -> context.resources.getStringArray(R.array.hot_webtoon_items)
            else -> null
        }
        return arrayString
    }

    fun getTitleTabItemString(tabItem: Int): String {
        val arrayString = when (tabItem) {
            0 -> "나만무료"
            1 -> "연재"
            2 -> "TOP100"
            3 -> "완결"
            else -> "뜨는한컷"
        }
        return arrayString
    }

    fun getTitleTabItemInt(tabItem: String): Int {
        val inte = when (tabItem) {
            "나만무료" -> 0
            "연재" -> 1
            "TOP100" -> 2
            "완결" -> 3
            else -> 4
        }
        return inte
    }

    fun getDayForKor2Eng(tabItem: String): String {
        val arrayString = when (tabItem) {
            "월" -> "mon"
            "화" -> "tue"
            "수" -> "wed"
            "목" -> "thu"
            "금" -> "fri"
            "토" -> "sat"
            "일" -> "sun"
            else -> "new"
        }
        return arrayString
    }
}