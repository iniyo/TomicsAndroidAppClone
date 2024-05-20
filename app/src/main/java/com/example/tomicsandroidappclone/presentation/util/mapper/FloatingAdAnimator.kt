package com.example.tomicsandroidappclone.presentation.util.mapper

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView

class FloatingAdAnimator(
    private val flAnimater: ViewGroup,
    private var duration: Long = 300L // 기본 지속 시간
) {
    private var isAdVisible = false
    private var isInitialized = false

    init {
        // 초기 상태 설정: 처음에 오른쪽 아래 모서리에 맞춰 보이지 않도록 설정
        flAnimater.visibility = View.GONE
        flAnimater.scaleX = 0.0f
        flAnimater.scaleY = 0.0f
    }

    // 계속 맨 처음엔 왼쪽 위에서부터 레이아웃이 확장되는 문제가 발생
    // addOnGlobalLayoutListener를 사용하여 화면에 그려진 뒤에 피벗을 설정.
    // view가 화면에 그려지기 전에 계속해서 위치값을 조정했으므로 설정이 안 되었던 것.
    private fun initializePivot() {
        if (!isInitialized) {
            flAnimater.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    flAnimater.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    // 피벗 설정
                    flAnimater.pivotX = flAnimater.width.toFloat()
                    flAnimater.pivotY = flAnimater.height.toFloat()
                    isInitialized = true
                }
            })
        }
    }

    // 스크롤 이벤트 설정
    fun setFloatingAd(nestedScrollView: NestedScrollView) {
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY && !isAdVisible) {
                showAd()
            } else if (scrollY == 0 && isAdVisible) {
                hideAd()
            }
        })
    }

    // 광고 표시 애니메이션
    private fun showAd() {
        initializePivot()
        if (!isAdVisible) {
            isAdVisible = true // 애니메이션 시작 전에 설정
            flAnimater.visibility = View.VISIBLE
            flAnimater.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(duration)
                .start()
        }
    }

    // 광고 숨김 애니메이션
    private fun hideAd() {
        if (isAdVisible) {
            flAnimater.animate()
                .alpha(0.0f)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .setDuration(duration)
                .withEndAction {
                    flAnimater.visibility = View.GONE
                    isAdVisible = false
                }
                .start()
        }
    }

    fun setDuration(newDuration: Long) {
        duration = newDuration
    }
}
