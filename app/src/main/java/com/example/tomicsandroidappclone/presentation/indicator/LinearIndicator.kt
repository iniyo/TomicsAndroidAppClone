package com.example.tomicsandroidappclone.presentation.indicator

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper

class LinearIndicator : LinearLayout {

    private var mContext: Context? = null

    private var mDefaultCircle: Int = 0
    private var mSelectCircle: Int = 0

    private var imageLine: MutableList<ImageView> = mutableListOf()

    // 픽셀 단위로
    private val temp = MyGraphicMapper().dp2px(2f)

    constructor(context: Context) : super(context) {

        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        mContext = context
    }

    /**
     * 기본 선 생성
     * @param count 선의 갯수
     * @param defaultLine 기본 선의 이미지
     * @param selectLine 선택된 선의 이미지
     * @param position 선택된 선의 포지션
     */
    fun createLinePanel(count: Int, defaultLine: Int, selectLine: Int, position: Int) {

        this.removeAllViews()

        mDefaultCircle = defaultLine
        mSelectCircle = selectLine

        for (i in 0 until count) {

            imageLine.add(ImageView(mContext).apply {
                setPadding(
                    temp.toInt(),
                    0,
                    temp.toInt(),
                    0
                )
            })

            this.addView(imageLine[i])
        }

        //인덱스 선택
        selectLine(position)
    }

    /**
     * 선택된 선 표시
     * @param position
     */
    fun selectLine(position: Int) {

        for (i in imageLine.indices) {

            if (i == position) {

                imageLine[i].setImageResource(mSelectCircle)

            } else {

                imageLine[i].setImageResource(mDefaultCircle)
            }

        }

    }
}