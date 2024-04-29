package com.example.tomicsandroidappclone.presentation.util.mapper

import android.util.Log
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class MyLogChecker {
    fun logCheck(log: String) {
        Log.d("TAG", "$log 실행")
    }

    fun logCheck(log: String, webtoon: Webtoon) {
        Log.d("TAG", "$log 실행, data check: ${webtoon.title}")
    }
}