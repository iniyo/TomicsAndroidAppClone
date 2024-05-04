package com.example.tomicsandroidappclone.presentation.util.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import com.google.android.material.tabs.TabLayout
class MyEasyTapControllHandler (tabLayout: TabLayout) : Handler(Looper.getMainLooper()) {
    private val mTabLayout = tabLayout
    private var titleTabText : String? = null
    private var detailTabText : String? = null

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when (msg.what) {
            0, 1, 2, 3, 4 -> updateDetailText(msg.what)
        }
    }

    private fun updateDetailText(tabType: Int) {
        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
        Log.d("TAG", "handleTabSelection detailTabText 변경: $detailTabText")
        // 기타 필요한 UI 업데이트나 데이터 처리 로직 추가
    }

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

    private fun setTabClickListener() {
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

    private fun handleTabSelection(position: Int, tabCount: Int, tab: TabLayout.Tab) {
        var message = obtainMessage(when (tabCount) {
            0 -> 0
            1 -> 1
            2 -> 2
            3 -> 3
            else -> 4
        })
        detailTabText = tab.text.toString()
        Log.d("TAG", "handleTabSelection detailTabText: $detailTabText")
        message.what = position; // 또는 기타 구분 가능한 값
        message.obj = tab.text.toString(); // 탭의 텍스트를 메시지 객체로 전달
        sendMessage(message)
        /*try {
            var message = obtainMessage(0) // what 값 설정. 구분값
            when (position) {
                in 0 until tabCount -> {
                    Log.d("TAG", "handleTabSelection detailTabText: ${tab.text.toString()} ")
                    detailTabText = tab.text.toString()
                    Log.d("TAG", "handleTabSelection: $titleTabText")
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(4))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(3))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = obtainMessage(1)
                        sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(2))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = obtainMessage(2)
                        sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(1))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = obtainMessage(3)
                        sendMessage(message)
                    }
                    if(titleTabText.equals(MyStringMapper().getTitleTabItemString(0))){
                        detailTabText = MyStringMapper().getDayForKor2Eng(detailTabText!!)
                        message = obtainMessage(4)
                        sendMessage(message)
                    }
                    Log.d("TAG", "handleTabSelection detailTabText 변경: $detailTabText ")

                }
                else -> {
                    throw NullPointerException("Invalid tab position")
                }
            }
        }catch (e: Exception) {
            throw e
        }*/
    }
}