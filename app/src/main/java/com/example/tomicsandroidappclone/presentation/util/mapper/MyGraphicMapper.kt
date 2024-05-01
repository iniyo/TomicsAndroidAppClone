package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.res.Resources
import android.util.DisplayMetrics


class MyGraphicMapper {
    private fun convertDensity(value: Float, isPxToDp: Boolean): Float {
        val resources = Resources.getSystem()
        val metrics = resources.displayMetrics

        return if (isPxToDp) {
            value / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            value * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    // px2
    fun px2dp(px: Int): Int = convertDensity(px.toFloat(), true).toInt()
    fun px2dp(px: Float): Float = convertDensity(px, true)
    // dp2
    fun dp2px(dp: Int): Int = convertDensity(dp.toFloat(), false).toInt()
    fun dp2px(dp: Float): Float = convertDensity(dp, false)
}