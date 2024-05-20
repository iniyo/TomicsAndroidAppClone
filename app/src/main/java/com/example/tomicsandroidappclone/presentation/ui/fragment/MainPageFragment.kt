package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.FragmentMainPageBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.ui.adapter.MainRecyclerAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerDefaultToonAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerTopSlideAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.handler.AutoScrollHandler
import com.example.tomicsandroidappclone.presentation.util.mapper.FloatingAdAnimator
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var myHandler: AutoScrollHandler
    private lateinit var binding: FragmentMainPageBinding
    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAG", "main fragment onCreateView 실행")
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        binding.incluedLayoutBanner.linearContainer.visibility = View.GONE
        return try {
            setAdapter()
            setFloatingAd()
            binding.root
        } catch (e: Exception) {
            Log.e("TAG", "main fragment onCreateView: ${e.message}")
            binding.root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "onDestroyView: ")
        binding.rvMainPage.adapter = null
    }

    private fun setFloatingAd() {
        binding.apply {
            btnCrossline.setOnClickListener {
                clAnimator.removeAllViews()
            }
            val floatingAdAnimator = FloatingAdAnimator(clAnimator,500)
            floatingAdAnimator.setFloatingAd(nestedScrollMain)
        }
    }

    private fun setViewPager(webtoonList: ArrayList<Webtoon>) {
        binding.apply {
            vpTopSlideItem.apply {
                myHandler = AutoScrollHandler(vpTopSlideItem)

                val interval = 3000
                myHandler.startAutoScroll(interval)

                setPageTransformer { page, position ->
                    val pagePosition = position % adapter!!.itemCount
                    val realPosition =
                        if (pagePosition < 0) pagePosition + adapter!!.itemCount else pagePosition
                    page.tag = realPosition
                }
                registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        adapter?.let { adapter ->
                            val itemCount = adapter.itemCount
                            val pagePosition = position - (Int.MAX_VALUE / 2)
                            Log.d("TAG", "position: $position")
                            val normalizedPosition = if (pagePosition >= 0) {
                                Log.d("TAG", "true: $pagePosition")
                                pagePosition
                            } else {
                                Log.d(
                                    "TAG",
                                    "false: ${itemCount - (pagePosition.absoluteValue + 1)}"
                                )
                                itemCount - (pagePosition.absoluteValue.inc())
                            }
                            val indicatorPosition = normalizedPosition % 7
                            Log.d("TAG", "onPageSelected: $indicatorPosition")
                            indicator.selectLine(indicatorPosition)
                        } ?: Log.w("TAG", "Adapter is null")
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE -> {
                                myHandler.startAutoScroll(interval)
                            }

                            ViewPager2.SCROLL_STATE_DRAGGING -> {
                                myHandler.stopAutoScroll()
                            }

                            else -> Log.d("TAG", "pageScrollState 예외 상태")
                        }
                    }
                })
                indicator.createLinePanel(
                    7,
                    R.drawable.indicator_line_off,
                    R.drawable.indicator_line_on,
                    0
                )
                adapter = ViewPagerTopSlideAdapter(webtoonList)

            }
            vpOffscreen.apply {
                incluedLayoutBanner.apply {
                    rgSelector.visibility = View.VISIBLE
                    linearContainer.apply {
                        visibility = View.VISIBLE
                        removeView(incluedLayoutBanner.tvSeeMore)
                    }
                    tvTag.text = resources.getStringArray(R.array.banner_items)[0]
                }
                clipToPadding = false
                clipChildren = false
                setPageTransformer { page, position ->
                    val offsetPx = MyGraphicMapper().offsetPx(requireContext())
                    page.translationX = position * offsetPx
                }
                adapter = ViewPagerDefaultToonAdapter(0, 10, null, webtoonList)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                offscreenPageLimit = 1
            }
        }
    }

    private fun setRecycler(webtoonList: ArrayList<Webtoon>) {
        val mainRecyclerAdapter = context?.let { MainRecyclerAdapter(webtoonList) }

        binding.rvMainPage.apply {
            adapter = mainRecyclerAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
            setItemViewCacheSize(6)
        }
    }

    private fun setAdapter() {
        viewModel.webtoonsInfo.observe(viewLifecycleOwner) {
            setRecycler(it)
            setViewPager(it)
        }
    }
}
