package com.example.tomicsandroidappclone.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.ActivityMainBinding
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.Fragments
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDrawerActions()
        setupTabNavigation()
        backPressed()
        setOnClick()
    }

    private fun setupDrawerActions() {
        binding.apply {
            ivDrawer.setOnClickListener {
                if (dlMain.isDrawerOpen(GravityCompat.START)) dlMain.closeDrawer(GravityCompat.START)
                else dlMain.openDrawer(GravityCompat.START)
            }
            activityDrawer.ivCloseDrawer.setOnClickListener {
                dlMain.closeDrawer(GravityCompat.START)
            }
        }
    }

    private fun setOnClick() {
        binding.apply {
            ivAddClose.setOnClickListener {
                clMainContainer.removeView(ivAddClose)
                clMainContainer.removeView(ivAdvertisement)
            }
            /*  ivSearch.setOnClickListener { setSearchLayout() }*/
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

    private fun backPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateHome()
            }
        })
    }

    /* private fun setSearchLayout() {
         // SearchLayoutBinding을 초기화
         val searchLayoutBinding = SearchLayoutBinding.bind(binding.root)

         // SearchPageAnimator 초기화 및 애니메이션 실행
         val searchPageAnimator = SearchPageAnimator(searchLayoutBinding)
         searchPageAnimator.animateSearchLayout()
     }*/

    private fun resetSelection(tabMap: Map<TextView, View>) {
        tabMap.keys.forEach { textView ->
            textView.setTextAppearance(R.style.TabTextNormal) // Set unselected text to normal
        }
        tabMap.values.forEach { it.isSelected = false }
    }

    private fun setTabMap(): Map<TextView, View> {
        val textViewMap: Map<TextView, View>
        binding.apply {
            // key to value
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
