package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import com.example.tomicsandroidappclone.R


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

    fun getnavigationBarHeight(context: Context) {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var navigationBarHeight = 0
        if (resourceId > 0) {
            navigationBarHeight = context.resources.getDimensionPixelSize(resourceId)
            Log.d("deviceSize", "navigation bar : ${px2dp(navigationBarHeight)}")
        }
    }

    // px2
    fun px2dp(px: Int): Int = convertDensity(px.toFloat(), true).toInt()
    fun px2dp(px: Float): Float = convertDensity(px, true)

    // dp2
    fun dp2px(dp: Int): Int = convertDensity(dp.toFloat(), false).toInt()
    fun dp2px(dp: Float): Float = convertDensity(dp, false)

    // offset
    fun offsetPx(context: Context): Int {
        val pageMarginPx = context.resources.getDimensionPixelOffset(R.dimen.page_margin)
        val pagerWidth = context.resources.getDimensionPixelOffset(R.dimen.page_width)
        val screenWidth = context.resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        return -offsetPx
    }

    // page 위치 조절
    /*private class PageDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = 30
        }
    }*/
}