package com.example.tomicsandroidappclone.presentation.util.handler

import android.os.Handler
import android.os.Looper
import com.google.android.material.tabs.TabLayout

class MyEasyTapControllHandler(tabLayout: TabLayout) : Handler(Looper.getMainLooper()) {
    private val mTabLayout = tabLayout
    var titleTabText: String? = null
        get() = field ?: "default title" // 기본 값 제공
        set(value) {
            field = value ?: "default title" // 기본 값 제공
        }

    var detailTabText: String? = null
        get() = field ?: "default detail" // 기본 값 제공
        set(value) {
            field = value ?: "default detail" // 기본 값 제공
        }

    fun addTabs(tabItems: Array<String>, checkType: Boolean) {
        mTabLayout.apply {
            removeAllTabs()
            if (checkType) {
                tabMode = TabLayout.MODE_SCROLLABLE
                addTabs(tabItems)
            } else {
                tabMode = TabLayout.MODE_AUTO
                tabGravity = TabLayout.GRAVITY_FILL
                addTabs(tabItems)
            }
        }
    }

    fun addTabs(tabItems: Array<String>) {
        mTabLayout.apply {
            removeAllTabs()
            val count = 0
            for (item in tabItems) {
                val newTab = newTab()
                newTab.setText(item)
                addTab(newTab)
                count.inc()
            }
        }
    }
}