package com.example.tomicsandroidappclone.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.tomicsandroidappclone.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    // 로딩되며 불러오는 동안 데이터를 가져오는 작업을 추가해야 함.

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlate()
        intent()
    }

    private fun setFlate() {
        binding = ActivitySplashBinding.inflate(layoutInflater) // databinding으로 set
        setContentView(binding.root)
    }

    private fun intent() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}