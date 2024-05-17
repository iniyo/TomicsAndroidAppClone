package com.example.tomicsandroidappclone.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.Fragments
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this)[BaseViewModel::class.java] }

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDrawerActions()
        setupTabNavigation()
        setOnClick()
    }

    private fun setupDrawerActions() {
        binding.apply {
            ivDrawer.setOnClickListener {
                if (dlMain.isDrawerOpen(GravityCompat.START)) dlMain.closeDrawer(GravityCompat.START)
                else dlMain.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun setOnClick() {
        binding.apply {
            ivAddClose.setOnClickListener {
                // rlAdContainer.removeAllViews()
            }
            ivTomicsLogo.setOnClickListener { navigateHome() }
        }
    }

    private fun navigateHome() {
        val tabMap = setTabMap()  // 탭 맵을 생성
        navigator.navigateTo(Fragments.MAIN_PAGE, "")
        resetSelection(tabMap)
    }

    private fun setupTabNavigation() {
        val tabMap = setTabMap()
        tabMap.keys.forEach { textView ->
            textView.setOnClickListener { navigateToTab(textView, tabMap) }
        }
    }

    private fun navigateToTab(textView: TextView, tabMap: Map<TextView, View>) {
        resetSelection(tabMap)
        tabMap[textView]?.isSelected = true
        textView.setTextAppearance(R.style.TabTextBold) // Set selected text to bold
        navigator.navigateTo(Fragments.WEBTOON_PAGE, textView.text.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateHome()
    }

    private fun resetSelection(tabMap: Map<TextView, View>) {
        tabMap.keys.forEach { textView ->
            textView.setTextAppearance(R.style.TabTextNormal) // Set unselected text to normal
        }
        tabMap.values.forEach { it.isSelected = false }
    }

    private fun setTabMap(): Map<TextView, View> {
        val textViewMap: Map<TextView, View>
        binding.apply {
            textViewMap = mapOf(
                tvFreeWebtoon to vFreeWebtoonUnderColorBar,
                tvSerialize to vSerializeUnderColorBar,
                tvTopHundred to vHundredUnderColorBar,
                tvEndedWebtoon to vEndedWebtoonUnderColorBar,
                tvHot to vHotWebtoonUnderColorBar
            )
        }
        return textViewMap
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
