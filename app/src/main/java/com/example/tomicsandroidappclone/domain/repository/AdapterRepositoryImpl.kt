package com.example.tomicsandroidappclone.domain.repository

import android.app.ActionBar.LayoutParams
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject


class AdapterRepositoryImpl @Inject constructor() : AdapterRepository {
    override fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>, checkType: Boolean) {
        tabLayout.removeAllTabs()
        if(checkType) {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            addTabs(tabLayout, tabItems)
        }else {
            tabLayout.tabMode = TabLayout.MODE_AUTO
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL
            addTabs(tabLayout, tabItems)
        }
    }
    override fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>) {
        tabLayout.removeAllTabs()
        val count = 0
        for(item in tabItems) {
            val newTab = tabLayout.newTab()
            newTab.setText(item)
            tabLayout.addTab(newTab)
            count.inc()
        }
    }
}