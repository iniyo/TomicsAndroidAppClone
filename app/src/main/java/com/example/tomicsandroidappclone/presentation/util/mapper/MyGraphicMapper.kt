package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.res.Resources
import android.util.DisplayMetrics


class MyGraphicMapper {
    fun px2dp(px: Int): Int {
        val resources: Resources = Resources.getSystem()
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    fun dp2px(dp: Int): Int {
        val resources: Resources = Resources.getSystem()
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}