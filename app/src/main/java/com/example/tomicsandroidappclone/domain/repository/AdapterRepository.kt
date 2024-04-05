package com.example.tomicsandroidappclone.domain.repository

import com.google.android.material.tabs.TabLayout

interface AdapterRepository {
    fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>)
}