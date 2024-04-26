package com.example.tomicsandroidappclone.presentation.util.adapter

import com.google.android.material.tabs.TabLayout

interface MyEasyAdapter {
    fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>)
    fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>, checkType: Boolean)
}