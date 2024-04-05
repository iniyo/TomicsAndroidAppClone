package com.example.tomicsandroidappclone.domain.repository

import com.google.android.material.tabs.TabLayout
import javax.inject.Inject


class AdapterRepositoryImpl @Inject constructor() : AdapterRepository {
    override fun addTabs(tabLayout: TabLayout,  tabItems: Array<String>) {
        tabLayout.removeAllTabs()
        for(item in tabItems) {
            val newTab = tabLayout.newTab()
            newTab.setText(item)
            tabLayout.addTab(newTab)
        }
    }

}