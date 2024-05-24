package com.example.tomicsandroidappclone.presentation.util.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyPreCacheLayoutManager {
    // 리사이클러뷰가 표시될 전체 사이즈를 내부적으로 늘려 onCreateViewHolder 호출 횟수를 줄임
    // 유의: 너무 큰 범위를 지정하게 되면 리사이클러뷰를 사용할 이유 없음.
    private class PreCacheLinearLayoutManager(context: Context, private val extraLayoutSpace: Int) :
        LinearLayoutManager(context) {
        @Deprecated("Deprecated in Java")
        override fun getExtraLayoutSpace(state: RecyclerView.State?) = extraLayoutSpace
    }

    private class PreCacheGridLayoutManager(
        context: Context,
        private val extraLayoutSpace: Int,
        spanCount: Int
    ) :
        GridLayoutManager(context, spanCount) {
        @Deprecated("Deprecated in Java")
        override fun getExtraLayoutSpace(state: RecyclerView.State?) = extraLayoutSpace
    }

    operator fun invoke(
        context: Context,
        layoutManager: RecyclerView.LayoutManager
    ): RecyclerView.LayoutManager {
        return when (layoutManager) {
            is GridLayoutManager -> PreCacheGridLayoutManager(context, 200, layoutManager.spanCount)
            is LinearLayoutManager -> PreCacheLinearLayoutManager(context, 200)
            else -> throw IllegalArgumentException("Unsupported LayoutManager type")
        }
    }
}
