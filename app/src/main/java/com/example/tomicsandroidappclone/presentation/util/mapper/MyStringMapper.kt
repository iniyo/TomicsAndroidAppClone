package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.example.tomicsandroidappclone.R

object MyStringMapper {
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

    fun getDayForEng2Kor(tabItem: String): Int {
        val dayToInt = when (tabItem) {
            "sun" -> 7
            "mon" -> 1
            "tue" -> 2
            "wed" -> 3
            "thu" -> 4
            "fri" -> 5
            "sat" -> 6
            else -> 8
        }
        return dayToInt
    }

    /**
     * 텍스트의 마지막 글자를 지정된 색상으로 변경하는 메서드.
     * @param context 컨텍스트
     * @param text 원본 텍스트
     * @param colorId 색상 리소스 ID
     * @return 색상이 적용된 SpannableString
     */
    fun setLastCharacterColor(context: Context, text: String, colorId: Int): SpannableString {
        val spannableString = SpannableString(text)
        val color = context.getColor(colorId)
        val startIndex = text.length - 1
        val endIndex = text.length

        spannableString.setSpan(
            ForegroundColorSpan(color), // 지정된 색상
            startIndex,
            endIndex,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }
}