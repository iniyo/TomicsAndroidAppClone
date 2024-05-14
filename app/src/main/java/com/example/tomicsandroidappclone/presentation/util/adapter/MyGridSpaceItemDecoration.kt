package com.example.tomicsandroidappclone.presentation.util.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyGridSpaceItemDecoration(
    private val spanCount: Int,
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작


        if (position >= 0) {
            val column = position % spanCount // item column
            outRect.apply {
                // spacing - column * ((1f / spanCount) * spacing)
                left = space - column * space / spanCount
                // (column + 1) * ((1f / spanCount) * spacing)
                right = (column + 1) * space / spanCount
                if (position < spanCount) top = space
                bottom = space
            }
        } else {
            outRect.apply {
                left = 0
                right = 0
                top = 0
                bottom = 0
            }
        }
    }
}