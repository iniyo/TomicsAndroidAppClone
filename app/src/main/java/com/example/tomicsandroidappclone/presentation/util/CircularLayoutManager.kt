package com.example.tomicsandroidappclone.presentation.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CircularLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun canScrollVertically(): Boolean = false

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (childCount == 0) return 0

        val direction = if (dx > 0) 1 else -1
        val absDx = Math.abs(dx)

        for (i in 0 until absDx) {
            val child = getChildAt(0) ?: break
            if (direction > 0) { // 스크롤 오른쪽
                if (getDecoratedRight(child) < width) { // 첫 번째 뷰가 화면 밖으로 나갔는지 확인
                    val movingView = recycler.getViewForPosition(0) // 처음 위치의 뷰를 재사용
                    removeAndRecycleView(child, recycler)
                    addView(movingView)
                    layoutDecorated(movingView, 0, 0, getDecoratedMeasuredWidth(movingView), getDecoratedMeasuredHeight(movingView))
                }
            } else { // 스크롤 왼쪽
                if (getDecoratedLeft(child) > 0) { // 마지막 뷰가 화면 밖으로 나갔는지 확인
                    val movingView = recycler.getViewForPosition(childCount - 1)
                    removeAndRecycleView(child, recycler)
                    addView(movingView, 0)
                    layoutDecorated(movingView, 0, 0, getDecoratedMeasuredWidth(movingView), getDecoratedMeasuredHeight(movingView))
                }
            }
        }
        offsetChildrenHorizontal(-dx) // 모든 자식 뷰를 dx만큼 이동
        return dx
    }
}