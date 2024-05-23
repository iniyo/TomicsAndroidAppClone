package com.example.tomicsandroidappclone.presentation.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.example.tomicsandroidappclone.R
import com.google.android.material.tabs.TabLayout

class CustomTabLayout(context: Context, attrs: AttributeSet?) : TabLayout(context, attrs) {

    private val paint = Paint().apply {
        color = context.getColor(R.color.tomics_red)
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private var indicatorX: Float = 0f
    private var indicatorWidth: Float = 10f
    private var indicatorHeight: Float = 10f
    private var indicatorOffset: Float = 15f // 인디케이터의 y 축 오프셋 값
    var titleTabText: String? = null
    var fixedTabIndex: Int = 0

    init {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                // 아무 동작도 하지 않음
            }

            override fun onTabUnselected(tab: Tab?) {}
            override fun onTabReselected(tab: Tab?) {}
        })

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 고정된 인디케이터 그리기
        if (titleTabText == "연재") {
            val selectedTab = getTabAt(fixedTabIndex)
            selectedTab?.view?.let {
                indicatorX = (it.left + it.right) / 2f
                val cx = indicatorX
                val cy = indicatorHeight + indicatorOffset // 상단에서 offset 만큼 밑으로 위치
                canvas.drawCircle(cx, cy, indicatorWidth / 2, paint)
            }
        }
    }

    override fun setScrollPosition(position: Int, positionOffset: Float, updateSelectedText: Boolean, updateIndicatorPosition: Boolean) {
        // 인디케이터를 고정된 상태로 유지하기 위해 기본 동작 무시
    }
}
