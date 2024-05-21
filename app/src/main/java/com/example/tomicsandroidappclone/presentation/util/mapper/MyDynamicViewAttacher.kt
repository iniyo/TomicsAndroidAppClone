package com.example.tomicsandroidappclone.presentation.util.mapper

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.tomicsandroidappclone.R

object MyDynamicViewAttacher {

    private var lastAddedViewId: Int? = null

    fun createTextView(
        context: Context,
        detail: String,
        color: Int = Color.BLACK,
        mTextSize: Float = 16f,
        padding: Int = 0,
        typeface: Typeface? = null,
        boolean: Boolean = false
    ): TextView {
        return TextView(context).apply {
            text = detail
            textSize = mTextSize
            setTextColor(color)
            if(boolean){
                setBackgroundResource(R.drawable.ic_shape_label)
            }
            setPadding(padding, padding, padding, padding)
            typeface?.let {
                setTypeface(it)
            }
        }
    }

    fun createImageView(
        context: Context,
        drawableId: Int,
        padding: Int
    ): ImageView {
        return ImageView(context).apply {
            setImageDrawable(ContextCompat.getDrawable(context, drawableId))
            setPadding(padding, padding, padding, padding)
            id = View.generateViewId()
        }
    }

    fun addViewPosition(parentLayout: ConstraintLayout, view: View, position: Position = Position.CENTER, marginTop: Int = 16) {
        val viewId = View.generateViewId()
        view.id = viewId

        // 뷰가 이미 다른 부모를 가지고 있는지 확인하고 제거
        (view.parent as? ViewGroup)?.removeView(view)

        parentLayout.addView(view)

        val constraintSet = ConstraintSet()
        constraintSet.clone(parentLayout) // layout 복제

        if (lastAddedViewId == null) {
            // 첫 번째 뷰인 경우 상단에 배치
            constraintSet.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, marginTop)
        } else {
            // 이전 뷰의 아래에 배치
            constraintSet.connect(viewId, ConstraintSet.TOP, lastAddedViewId!!, ConstraintSet.BOTTOM, marginTop)
        }

        // 뷰의 가로 위치 설정
        when (position) {
            Position.LEFT -> {
                constraintSet.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
                constraintSet.clear(viewId, ConstraintSet.END)
            }
            Position.CENTER -> {
                constraintSet.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
                constraintSet.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
            }
            Position.RIGHT -> {
                constraintSet.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
                constraintSet.clear(viewId, ConstraintSet.START)
            }
        }

        // 부착
        constraintSet.applyTo(parentLayout)

        // id 갱신
        lastAddedViewId = viewId
    }

    enum class Position {
        LEFT, CENTER, RIGHT
    }

}
