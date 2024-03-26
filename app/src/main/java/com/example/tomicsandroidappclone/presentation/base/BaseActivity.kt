package com.example.tomicsandroidappclone.presentation.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel 인스턴스를 만들려면 Provider가 필요 this는 owner 즉, 현재 사용되는 앱 컴포넌트를 뜻함. -> BaseViewModel
    private val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }
    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) // databinding으로 set
        binding.viewModel = baseViewModel // ViewModel 사용
        // 초기값 설정.
        initViews()
    }

    private fun initViews() {
        setupDrawer()
        setupTabs()
    }

    private fun setupDrawer() {
        /*binding.ivDrawer.setOnClickListener {
            if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.ivTomicsLogo.setOnClickListener {
            navigator.navigateTo(Screens.TAB1)
        }
        binding.activityDrawer.ivCloseDrawer.setOnClickListener{
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
        }*/
    }

    private fun setupTabs() {

    }

    private fun subscribeToViewModel() {

        baseViewModel.webtoons.observe(this){
            binding.tvFreeWebtoon.text = baseViewModel.webtoonInfo.value?.webtoons!![0].title
            Glide.with(binding.ivTomicsLogo.context).load(baseViewModel.webtoonInfo.value?.webtoons!![0].img).into(binding.ivTomicsLogo)
        }
        Log.d("TAG", baseViewModel.webtoonInfo.value?.webtoons!![0].title)
        /*baseViewModel.webtoonInfo.observe(this){
            binding.tvTest.text = baseViewModel.webtoonInfo.value?.webtoons!![0].title
        }*/
    }

    override fun onStart() {
        super.onStart()
        baseViewModel.fetchWebtoons()
        subscribeToViewModel()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onResume() {
        super.onResume()

    }
}