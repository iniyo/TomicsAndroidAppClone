package com.example.tomicsandroidappclone.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.tomicsandroidappclone.databinding.ActivitySplashBinding


class SplashAcitivty: AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loading()
    }
    fun loading() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, BaseActivity::class.java))
            finish()
        },2000)
    }
}