package com.example.tomicsandroidappclone.presentation.util.adapter

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import com.google.android.material.tabs.TabLayout
class MyEasyTapController(tabLayout: TabLayout) {
    private val mTabLayout = tabLayout
    private var titleTabText : String? = null
    private var detailTabText : String? = null
    private val handler = Handler(Looper.getMainLooper())

    fun addTabs(tabItems: Array<String>, checkType: Boolean) {
        mTabLayout.removeAllTabs()
        if (checkType) {
            mTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            addTabs(tabItems)
        } else {
            mTabLayout.tabMode = TabLayout.MODE_AUTO
            mTabLayout.tabGravity = TabLayout.GRAVITY_FILL
            addTabs(tabItems)
        }
    }

    fun addTabs(tabItems: Array<String>) {
        mTabLayout.removeAllTabs()
        val count = 0
        for (item in tabItems) {
            val newTab = mTabLayout.newTab()
            newTab.setText(item)
            mTabLayout.addTab(newTab)
            count.inc()
        }
        setTabClickListener()
    }

    fun getTitleTabText() : String {
        return try {
            titleTabText!!
        }catch (e: Exception){
            e.message.toString()
        }
    }

    fun getDetailTabText() : String {
        return try {
            detailTabText!!
        }catch (e: Exception){
            e.message.toString()
        }
    }

    fun setTabClickListener() {

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                   val position = it.position // Get the selected tab's position
                    val tabCount = mTabLayout.tabCount
                    try {
                        titleTabText = when(tabCount){
                            2 -> MyStringMapper().getTitleTabItemString(4)
                            3 -> MyStringMapper().getTitleTabItemString(3)
                            5 -> MyStringMapper().getTitleTabItemString(2)
                            8 -> MyStringMapper().getTitleTabItemString(1)
                            else -> MyStringMapper().getTitleTabItemString(0)
                        }
                        Log.d("TAG", "handleTabSelection titleTabText: $titleTabText ")
                        handleTabSelection(position, mTabLayout.tabCount, tab)
                    }catch (e: Exception){
                        Log.e("TAG", "onTabSelected: ${e.message}")
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("TAG", "onTabUnselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("TAG", "onTabReselected")
            }
        })
    }

    fun handleTabSelection(position: Int, tabCount: Int, tab: TabLayout.Tab) {
        try {
            var message = handler.obtainMessage(0) // what 값 설정. 구분값
            when (position) {
                in 0 until tabCount -> {
                    Log.d("TAG", "handleTabSelection detailTabText: ${tab.text.toString()} ")
                    detailTabText = tab.text.toString()
                    Log.d("TAG", "handleTabSelection: $titleTabText")
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(4))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        handler.sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(3))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = handler.obtainMessage(1)
                        handler.sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(2))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = handler.obtainMessage(2)
                        handler.sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(1))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = handler.obtainMessage(3)
                        handler.sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(0))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = handler.obtainMessage(4)
                        handler.sendMessage(message)
                    }
                    Log.d("TAG", "handleTabSelection detailTabText 변경: $detailTabText ")

                }
                else -> {
                    throw NullPointerException("Invalid tab position")
                }
            }
        }catch (e: Exception) {
            throw e
        }

    }
}