package com.example.tomicsandroidappclone.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivitySplashBinding
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.mapper.MyCalendar
import com.example.tomicsandroidappclone.presentation.util.navigator.Activitys
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    @Inject
    lateinit var navigator: AppNavigator
    private val baseViewModel: BaseViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 3000L // 3초

    private val navigateToMainRunnable = Runnable {
        navigateToMainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 데이터 로딩 시작
        baseViewModel.loadWebtoonData(MyCalendar.invoke())
        baseViewModel.webtoonsInfo.observe(this) {
            if(it != null){
                navigateToMainActivity()
            }
        }


        // 3초 후에 강제로 MainActivity로 전환
        handler.postDelayed(navigateToMainRunnable, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(navigateToMainRunnable)
    }

    private fun navigateToMainActivity() {
        if (!isFinishing) {
            navigator.navigateTo(Activitys.MAIN_ACTIVITY)
            finish()
        }
    }

}