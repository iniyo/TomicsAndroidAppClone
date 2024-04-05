/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.tomicsandroidappclone.presentation.navigator
import androidx.fragment.app.FragmentActivity
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.presentation.fragment.*
import javax.inject.Inject

/**
 * Navigator implementation.
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: MainFragments) {
        val fragment = when (screen) {
            MainFragments.MAIN_PAGE -> MainWebtoonPageFragment.newInstance()
            MainFragments.TAB1 -> FreeWebtoonPageFragment.newInstance()
            MainFragments.TAB2 -> SerializePageFragment.newInstance()
            MainFragments.TAB3 -> TopWebtoonPageFragment.newInstance()
            MainFragments.TAB4 -> EndedWebtoonPageFragment.newInstance()
            MainFragments.TAB5 -> HotWebtoonPageFragment.newInstance()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}
