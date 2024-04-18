package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemViewpagerBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.ui.adapter.auto_scroll_handler.AutoScrollHandler


class MainPageAdapter(
    private val webtoon: List<Webtoon>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        TYPE_TOP_TOON, TYPE_DEFAULT_TOON_LIST, OTHER_TYPE
    }

    data class MainItem(val viewType: ViewType, val webtoonData: List<Webtoon>, val choose: Int)

    val itemList = listOf(
        MainItem(ViewType.TYPE_TOP_TOON, webtoon, -1),
        MainItem(ViewType.TYPE_TOP_TOON, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 1),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 2),
        MainItem(ViewType.OTHER_TYPE, webtoon, 1),
    )

    inner class ViewPagerViewHolder(private val binding: RecyclerMainItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            setViewPager(binding, itemList[position].choose, webtoon)
        }
    }

    inner class RecyclerViewHolder(private val binding: RecyclerMainItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            setRecyclerView(binding, itemList[position].choose, webtoon)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "MainPage onBindViewHolder: 실행")
        val id = getItemId(position)
        Log.d("TAG", "MainPage onBindViewHolder: $id")

        val viewType = getItemViewType(position) // ViewType 추출
        when (ViewType.entries[viewType]) {
            ViewType.TYPE_TOP_TOON -> {

                (holder as ViewPagerViewHolder).bind(position)
            }

            ViewType.TYPE_DEFAULT_TOON_LIST -> {
                holder.itemView.tag = position
                (holder as RecyclerViewHolder).bind(position)
            }

            ViewType.OTHER_TYPE -> {
                holder.itemView.tag = position
                (holder as RecyclerViewHolder).bind(position)
            }
        }
    }

    private class PageDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = margin
            outRect.right = margin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "MainPage onCreateViewHolder: 실행")
        return when (ViewType.entries[viewType]) {
            ViewType.TYPE_TOP_TOON -> {
                ViewPagerViewHolder(
                    RecyclerMainItemViewpagerBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }

            ViewType.TYPE_DEFAULT_TOON_LIST -> {
                RecyclerViewHolder(
                    RecyclerMainItemRecyclerviewBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }
            // 미정
            ViewType.OTHER_TYPE -> {
                RecyclerViewHolder(
                    RecyclerMainItemRecyclerviewBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }
        }
    }

    // entries : 열거형의 모든 값을 배열로 반환
    // ordinal : entries[position]의 순서 반환 -> 특정(position) 배열 값 반환
    // Int 자료형으로 ViewType.entries[position].ordinal값을 반환
    override fun getItemViewType(position: Int): Int = itemList[position].viewType.ordinal
    override fun getItemCount(): Int = itemList.size

    private fun setViewPager(
        binding: RecyclerMainItemViewpagerBinding,
        choose: Int,
        webtoonList: List<Webtoon>
    ) {
        val adapterType = when (choose) {
            -1 -> {
                val myHandler = AutoScrollHandler(binding.vpMain)
                val interval = 3000
                myHandler.startAutoScroll(interval)

                binding.vpMain.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE -> {
                                myHandler.startAutoScroll(interval)
                            }

                            ViewPager2.SCROLL_STATE_DRAGGING -> {
                                myHandler.stopAutoScroll()
                                myHandler.startAutoScroll(interval)
                            }

                            else -> Log.d("TAG", "pageScrollState 예외 상태")
                        }
                    }
                })
                TopSlideAdapter(webtoonList.take(7) as ArrayList<Webtoon>)
            }

            0 -> {
                val itemDecoration = object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.left = 30 // 왼쪽 여백 설정
                    }
                }
                // pixeloffset은 Int형
                val pageMarginPx = context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
                val pagerWidth = context.resources.getDimensionPixelOffset(R.dimen.pagerWidth)
                val screenWidth = context.resources.displayMetrics.widthPixels
                val offsetPx = screenWidth - pageMarginPx - pagerWidth
                binding.recyclerMainViewpagerContainer.removeView(binding.llTopSlideObserver) // 재사용 되지 않으므로 그냥 view를 삭제.
                binding.vpMain.apply {
                    clipToPadding = false // padding 공간을 스크롤 화면으로 활용가능.
                    clipChildren = false // view의 자식들이 영역 안에서만 그려지도록 설정하는 것. Default = true
                    setPageTransformer { page, position ->
                        page.translationX = position * -offsetPx
                    }
                    addItemDecoration(itemDecoration)
                }
                PopularityToonAdapter(webtoonList as ArrayList<Webtoon>, choose)
            }
            else -> {
                PopularityToonAdapter(webtoonList as ArrayList<Webtoon>, choose)
            }
        }

        /* 참고 : https://velog.io/@king-jungin/Android-%EC%96%91-%EC%98%86%EC%9D%B4-%EB%AF%B8%EB%A6%AC%EB%B3%B4%EC%9D%B4%EB%8A%94-ViewPager2-%EB%A7%8C%EB%93%A4%EA%B8%B0 */

        binding.vpMain.apply {
            adapter = adapterType
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1
            setCurrentItem(0, true)
        }
    }

    private fun setRecyclerView(
        binding: RecyclerMainItemRecyclerviewBinding,
        choose: Int,
        webtoonList: List<Webtoon>
    ) {
        val context = binding.root.context
        val bannerItems = context.resources.getStringArray(R.array.banner_items)
        for (i in bannerItems.indices) {
            Log.d("TAG", "bannerItems $bannerItems[i]")
        }

        val adapterType = when (choose) {
            -1 -> {
                val itemDecoration = object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildItemId(view)

                        Log.d("TAG", "getItemID: $position")
                        val itemWidth = parent.width / 3 // 아이템 너비 계산

                        if (position.toInt() == 0) { // 첫 번째 아이템
                            outRect.left = itemWidth // 왼쪽 여백 설정
                        } else {
                            outRect.left = -50 // 여백 없음
                        }
                    }
                }
                val snapHelper = PagerSnapHelper()
                binding.rvMainDefaultList.apply {
                    snapHelper.attachToRecyclerView(binding.rvMainDefaultList)
                    addItemDecoration(itemDecoration)
                }
                PopularityToonAdapter(webtoonList as ArrayList<Webtoon>, choose)
            }

            0 -> DefaultToonListAdapter(webtoonList, choose)
            else -> {
                DefaultToonListAdapter(webtoonList, choose)
            }
        }

        val setLayoutManager = LinearLayoutManager(
            binding.rvMainDefaultList.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // setLayoutManager.recycleChildrenOnDetach = true // 아이템 뿐 아니라 아이템 내의 View도 모두 재사용 됨.
        binding.rvMainDefaultList.apply {
            adapter = adapterType
            layoutManager = setLayoutManager
            setItemViewCacheSize(5) // 아이템 화면 밖으로 사라져도, n만큼의 항목을 계속 유지 -> 캐싱해두는것. onBind를 실행하지 않아도 됨.
            setHasFixedSize(true)
        }
    }
}