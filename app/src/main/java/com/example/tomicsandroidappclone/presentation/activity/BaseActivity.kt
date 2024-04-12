package com.example.tomicsandroidappclone.presentation.activity


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.Fragments
import com.example.tomicsandroidappclone.presentation.viewmodel.activity_view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // ViewModel 인스턴스를 만들려면 Provider가 필요 this는 owner 즉, 현재 사용되는 앱 컴포넌트를 뜻함. -> BaseViewModel
    private val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 초기값 설정.
        initViews()
    }

    private fun initViews() {
        setUpFlate()
        setUpDrawer()
        setUpTabNavigator()
    }

    private fun setUpFlate() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) // databinding으로 set
        binding.viewModel = baseViewModel // ViewModel 연동
    }

    private fun setUpDrawer() {
        binding.ivDrawer.setOnClickListener {
            if (binding.dlMain.isDrawerOpen(GravityCompat.START)) {
                binding.dlMain.closeDrawer(GravityCompat.START)
            } else {
                binding.dlMain.openDrawer(GravityCompat.START)
            }
        }
        binding.ivTomicsLogo.setOnClickListener {
            navigator.navigateTo(Fragments.MAIN_PAGE, setTabItems(binding.tvFreeWebtoon) )
        }
        binding.activityDrawer.ivCloseDrawer.setOnClickListener {
            binding.dlMain.closeDrawer(GravityCompat.START)
        }
    }

    private fun setUpTabNavigator() {
        binding.ivTomicsLogo.setOnClickListener {
            navigator.navigateTo(Fragments.MAIN_PAGE, setTabItems(binding.tvFreeWebtoon))
        }
        binding.rlFreeWebtoon.setOnClickListener {
            navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(binding.tvFreeWebtoon))
        }
        binding.rlSerialize.setOnClickListener {
            navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(binding.tvSerialize))
        }
        binding.rlTopHundred.setOnClickListener {
            navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(binding.tvTopHundred))
        }
        binding.rlEndedWebtoon.setOnClickListener {
            navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(binding.tvEndedWebtoon))
        }
        binding.rlHotWebtoon.setOnClickListener {
            navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(binding.tvHot))
        }
    }

    private fun setTabItems(textView: TextView) : Array<String> {
        val arrayString = when(textView.text){
            "나만무료" -> resources.getStringArray(R.array.free_webtoon_tab_items)
            "연재" -> resources.getStringArray(R.array.serialize_tab_items)
            "TOP100" -> resources.getStringArray(R.array.top_webtoon_items)
            "완결" -> resources.getStringArray(R.array.ended_webtoon_items)
            else -> resources.getStringArray(R.array.hot_webtoon_items)
        }
        return arrayString
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Activity Start ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Activity Stop ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Activity Resume ")
    }

}