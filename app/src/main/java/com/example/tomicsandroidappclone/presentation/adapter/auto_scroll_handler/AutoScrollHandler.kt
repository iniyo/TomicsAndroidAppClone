package com.example.tomicsandroidappclone.presentation.adapter.auto_scroll_handler

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

class AutoScrollHandler(private val viewPager: ViewPager2) : Handler(Looper.getMainLooper()) {
    private var isAutoScroll = false

    fun startAutoScroll(interval: Int) {
        Log.d("TAG", "startAutoScroll")
        isAutoScroll = true
        postAutoScroll(interval)
    }

    fun stopAutoScroll() {
        Log.d("TAG", "stopAutoScroll")
        isAutoScroll = false
        removeCallbacksAndMessages(null)
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)

        val currentItem = viewPager.currentItem
        viewPager.setCurrentItem(currentItem+1, 400)
        Log.d("TAG", "handle currentItem$currentItem")
    }

    private fun postAutoScroll(interval: Int) {
        if (isAutoScroll) {
            sendEmptyMessageDelayed(0, interval.toLong())
        }
    }

    private fun ViewPager2.setCurrentItem(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0
        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { beginFakeDrag() }
            override fun onAnimationEnd(animation: Animator) { endFakeDrag() }
            override fun onAnimationCancel(animation: Animator) { /* Ignored */ }
            override fun onAnimationRepeat(animation: Animator) { /* Ignored */ }
        })
        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()
    }
}