package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.FragmentMainPageBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.ui.adapter.MainRecyclerAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerDefaultToonAdapter
import com.example.tomicsandroidappclone.presentation.ui.adapter.ViewPagerTopSlideAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.BaseViewModel
import com.example.tomicsandroidappclone.presentation.util.handler.AutoScrollHandler
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var myHandler: AutoScrollHandler
    private lateinit var binding: FragmentMainPageBinding

    // lateinit과 lazy의 공통점 : ?일수 없다, 나중에 값을 초기화 한다.
    // lateinit과 lazy의 차이점 : late는 var로만 by lazy는 val로만 선언 된다. ()
    // 즉, 초기화 이후 값이 변하는 유무에 따라 사용하며 구분하면 lateinit: 값이 바뀔때, by lazy: 읽기 전용일때
    private val viewModel: BaseViewModel by lazy { ViewModelProvider(requireActivity())[BaseViewModel::class.java] }

    companion object {
        fun newInstance() = MainPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAG", "main fragment onCreateView 실행")
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        binding.incluedLayoutBanner.linearContainer.visibility = View.GONE
        return try {
            setAdapter()
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

    // fragment가 완전히 파괴되기 전 view가 해제되는 경우, adapter는 뷰에 표시되기 때문에.
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "onDestroyView: ")
        binding.rvMainPage.adapter = null // Adapter 해제
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
                    // pagePosition 값을 사용하여 page.tag에 값을 설정
                    page.tag = realPosition
                }
                registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        adapter?.let { adapter ->
                            val itemCount = adapter.itemCount // int.maxvalue
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
                                itemCount - (pagePosition.absoluteValue.inc())// 음수일 경우 역순으로 위치 계산. +1을 해야 값이 중복되지 않음.
                            }
                            val indicatorPosition = normalizedPosition % 7
                            Log.d("TAG", "onPageSelected: $indicatorPosition")
                            binding.indicator.selectLine(indicatorPosition)
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
                binding.indicator.createLinePanel(
                    7,
                    R.drawable.indicator_line_off,
                    R.drawable.indicator_line_on,
                    0
                )
                val aadapter = ViewPagerTopSlideAdapter(webtoonList)
                adapter = aadapter

                /*adapter = ViewPagerTopSlideAdapter(webtoonList.take(7))*/
            }
            vpOffscreen.apply {
                incluedLayoutBanner.apply {
                    rgSelector.visibility = View.VISIBLE
                    linearContainer.apply {
                        visibility = View.VISIBLE
                        removeView(incluedLayoutBanner.tvSeeMore)
                    }
                }
                clipToPadding = false // padding 공간을 스크롤 화면으로 활용가능.
                clipChildren = false // view의 자식들이 영역 안에서만 그려지도록 설정하는 것. Default = true
                setPageTransformer { page, position ->
                    page.translationX = position * MyGraphicMapper().offsetPx(context)
                }
                adapter = ViewPagerDefaultToonAdapter(0, 10, null ,webtoonList)
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
            /*layoutManager = PreCacheLayoutManager(binding.root.context, 600)*/
            setItemViewCacheSize(6)  // UI가 화면에서 사라졌을 때 pool에 들어가지 않고 cache됨. 따라서 bindHolder 호출 없이 보여짐.
            // nested scroll view안에서 위 아래로 움직이다가 화면을 꾹 누르면 화면이 심하게 흔들리는 현상 발생.
            /*isNestedScrollingEnabled = false*/
        }
    }

    private fun setAdapter() {
        // context는 LifeCycle과 연결되어 있고(!) Singleton임. (실행 중 하나의 객체만 가짐)
        // (!) ApplicationContext, ActivityContext, FragmentContext 는 이름에 있는 LifeCycle을 가짐.
        Log.d("TAG", "${viewModel.webtoonsInfo}")
        viewModel.webtoonsInfo.observe(viewLifecycleOwner) {
            // observing.. adapter 초기화 코드를 실행.
            setRecycler(it)
            setViewPager(it)
        }
    }
}

