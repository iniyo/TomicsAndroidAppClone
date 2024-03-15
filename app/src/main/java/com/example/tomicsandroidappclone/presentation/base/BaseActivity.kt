package com.example.tomicsandroidappclone.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.navigator.*
import com.example.tomicsandroidappclone.presentation.viewmodel.BaseViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var baseViewModel: BaseViewModel
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel 인스턴스를 만들려면 Provider가 필요
        // ViewModel 사용, this - Owner를 뜻함. -> 여기서 this는 BaseActivity를 말하므로 Owner는 BaseActivity
        // 즉, ViewModelStore를 BaseActivity가 소유하고 있다는 뜻.
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        binding.viewModel = baseViewModel //data binding으로 Layout에 viewModel 연결

        initViews()
        setupDrawer()
        setupTabs()
        setListeners()
    }

    private fun initViews() {
        binding.ivDrawer.setOnClickListener {
            if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.ivTomicsLogo.setOnClickListener {
            navigator.navigateTo(Screens.TAB1)
        }
    }

    private fun setupDrawer() {
        // drawer 설정 코드
    }

    private fun setupTabs() {
        val array = resources.getStringArray(R.array.news_frgament_array)
        binding.tlMain.removeAllTabs()
        for (item in array) {
            binding.tlMain.addTab(binding.tlMain.newTab().setText(item))
        }

        binding.tlMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 탭 선택 시 처리할 코드
                when (tab.position) {
                    0 -> navigator.navigateTo(Screens.TAB2)
                    1 -> navigator.navigateTo(Screens.TAB3)
                    2 -> navigator.navigateTo(Screens.TAB4)
                    3 -> navigator.navigateTo(Screens.TAB5)
                    4 -> navigator.navigateTo(Screens.TAB6)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 탭 선택 해제 시 처리할 코드
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // 탭이 이미 선택된 상태에서 다시 선택되었을 때 처리할 코드
            }
        })
    }

    private fun setListeners() {
        // 리스너 설정 코드
    }

    private fun subscribeToViewModel() {
        // ViewModel 구독 코드
    }

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
    }

    override fun onStop() {
        super.onStop()
        // ViewModel 구독 해제
       /* baseViewModel.clear()*/
    }
}