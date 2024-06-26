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


package com.example.tomicsandroidappclone.presentation.util.navigator

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.presentation.ui.MainActivity
import com.example.tomicsandroidappclone.presentation.ui.SplashActivity
import javax.inject.Inject

/**
 * Navigator implementation.
 */
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    private val navController: NavController by lazy {
        (activity.supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment).navController
    }

    override fun navigateTo(screen: Fragments, tab: String) {
        when (screen) {
            Fragments.MAIN_PAGE -> {
                navController.navigate(R.id.mainPageFragment)
            }

            Fragments.WEBTOON_PAGE -> {
                val bundle = Bundle().apply {
                    putString("tab", tab)
                }
                navController.navigate(R.id.webtoonPageFragment, bundle)
            }
        }
    }

    override fun navigateTo(screen: Activitys) {
        when (screen) {
            Activitys.MAIN_ACTIVITY -> {
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }

            Activitys.SPLASH_ACTIVITY -> {
                val intent = Intent(activity, SplashActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }
}
