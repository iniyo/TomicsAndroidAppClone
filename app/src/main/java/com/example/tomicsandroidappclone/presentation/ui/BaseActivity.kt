package com.example.tomicsandroidappclone.presentation.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.mapper.ProgressDialog
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.Fragments
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }
    // ViewModel 인스턴스를 만들려면 Provider가 필요 this는 owner 즉, 현재 사용되는 앱 컴포넌트를 뜻함. -> BaseActivity

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 초기값 설정.
        initViews()
    }

    private fun initViews() {
        /*setData()*/
        setFlate()
        setDrawer()
        setTabNavigator()
    }

    private fun setData() {
        var loaded = false // Initialize a flag to track data loading status
        baseViewModel.webtoonsInfo.observe(this) { data ->
            Log.d("TAG", "setData: ")
        }
    }

    private fun setFlate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*DataBindingUtil.setContentView(this, R.layout.activity_main) // databinding으로 set
        binding.viewModel = baseViewModel // ViewModel 연동*/
    }

    private fun setDrawer() {
        binding.apply {
            ivDrawer.setOnClickListener {
                if (dlMain.isDrawerOpen(GravityCompat.START)) {
                    dlMain.closeDrawer(GravityCompat.START)
                } else {
                    dlMain.openDrawer(GravityCompat.START)
                }
            }
            ivTomicsLogo.setOnClickListener {
                navigator.navigateTo(Fragments.MAIN_PAGE, setTabItems(tvFreeWebtoon))
            }
            activityDrawer.ivCloseDrawer.setOnClickListener {
                dlMain.closeDrawer(GravityCompat.START)
            }
        }
    }

    private fun setCustomProgressDialog() {
        val customProgressDialog = ProgressDialog(binding.root.context)
        customProgressDialog.show()
    }

    private fun setTabNavigator() {

        binding.apply {
            // 각 탭에 대한 클릭 리스너 설정
            listOf(
                tvFreeWebtoon,
                tvSerialize,
                tvTopHundred,
                tvEndedWebtoon,
                tvHot
            ).forEach { textView ->
                textView.setOnClickListener { clickedView ->

                    // 클릭된 텍스트 뷰와 해당하는 하단 바 뷰를 찾아서 isSelected 설정
                    val selectedBarView = when (clickedView) {
                        tvFreeWebtoon -> vFreeWebtoonUnderColorBar
                        tvSerialize -> vSerializeUnderColorBar
                        tvTopHundred -> vHundredUnderColorBar
                        tvEndedWebtoon -> vEndedWebtoonUnderColorBar
                        tvHot -> vHotWebtoonUnderColorBar
                        else -> null
                    }

                    // 모든 하단 바 뷰에 대해 isSelected를 false로 설정
                    listOf(
                        vFreeWebtoonUnderColorBar,
                        vSerializeUnderColorBar,
                        vHundredUnderColorBar,
                        vEndedWebtoonUnderColorBar,
                        vHotWebtoonUnderColorBar
                    ).forEach { barView ->
                        barView.isSelected = barView == selectedBarView
                    }

                    // 해당 탭 아이템에 대한 웹툰 페이지로 탐색
                    navigator.navigateTo(Fragments.WEBTOON_PAGE, setTabItems(textView))
                }
            }
        }
    }

    private fun setTabItems(textView: TextView): Array<String> {
        val arrayString = when (textView.text) {
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