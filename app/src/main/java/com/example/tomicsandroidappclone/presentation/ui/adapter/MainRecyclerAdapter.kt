package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemViewpagerBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.ui.adapter.handler.AutoScrollHandler
import com.example.tomicsandroidappclone.presentation.util.mapper.MyLogChecker

class MainRecyclerAdapter(
    private val webtoon: ArrayList<Webtoon>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var myHandler: AutoScrollHandler

    enum class ViewType {
        TYPE_VIEWPAGER, TYPE_RECYCLER
    }
    data class MainItem(val viewType: ViewType, val webtoonData: ArrayList<Webtoon>, val choose: Int)

    val itemList = listOf(
        // viewpager choose -1=top slide, 0=nested recycler
        // recycler -1=only ad 0=default, 1=big size, 2=middle size, 3=tag to image, 4=change container, 5=long size banner
        MainItem(ViewType.TYPE_VIEWPAGER, webtoon, -1),
        MainItem(ViewType.TYPE_VIEWPAGER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 1),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 2),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, -1),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 3),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0), // tag 추가해서 선택 가능하게 변경.
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 4),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, -1),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 4),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 0),
        MainItem(ViewType.TYPE_RECYCLER, webtoon, 5),
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
            ViewType.TYPE_VIEWPAGER -> (holder as ViewPagerViewHolder).bind(position)
            ViewType.TYPE_RECYCLER -> (holder as RecyclerViewHolder).bind(position)
        }
    }

    /*private class PageDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = 30
        }
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "MainPage onCreateViewHolder: 실행")
        return when (ViewType.entries[viewType]) {
            ViewType.TYPE_VIEWPAGER -> {
                ViewPagerViewHolder(
                    RecyclerMainItemViewpagerBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }

            ViewType.TYPE_RECYCLER -> {
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        /*myHandler.startAutoScroll(3000)*/ //초기화 문제
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.d("TAG", "onDetachedFromRecyclerView ")
        myHandler.stopAutoScroll()
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
                myHandler = AutoScrollHandler(binding.vpMain)
                val interval = 3000
                // 여기에다가 사용하게 되면 handler가 view가 사라질 때 사라지지 않음.
                // 따라서 view가 사라질때 사라지게 하거나 다른 방법을 찾아야 함.
                myHandler.startAutoScroll(interval)
                binding.incluedLayoutBanner.linearContainer.visibility = View.GONE

                binding.vpMain.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrollStateChanged(state: Int) {
                        super.onPageScrollStateChanged(state)
                        when (state) {
                            ViewPager2.SCROLL_STATE_IDLE -> {
                                myHandler.startAutoScroll(interval)
                            }

                           /* ViewPager2.SCROLL_STATE_DRAGGING -> {
                                myHandler.stopAutoScroll()
                                myHandler.startAutoScroll(interval)
                            }*/

                            else -> Log.d("TAG", "pageScrollState 예외 상태")
                        }
                    }
                })
                ViewPagerTopSlideAdapter(webtoonList.take(7) as ArrayList<Webtoon>)
            }

            0 -> {
                // pixeloffset은 Int형
                val pageMarginPx = context.resources.getDimensionPixelOffset(R.dimen.page_margin)
                val pagerWidth = context.resources.getDimensionPixelOffset(R.dimen.page_width)
                val screenWidth = context.resources.displayMetrics.widthPixels
                val offsetPx = screenWidth - pageMarginPx - pagerWidth
                binding.recyclerMainViewpagerContainer.removeView(binding.llTopSlideObserver) // 재사용 되지 않으므로 그냥 view를 삭제.
                binding.vpMain.apply {
                    clipToPadding = false // padding 공간을 스크롤 화면으로 활용가능.
                    clipChildren = false // view의 자식들이 영역 안에서만 그려지도록 설정하는 것. Default = true
                    setPageTransformer { page, position ->
                        page.translationX = position * -offsetPx
                    }

                }
                ViewPagerDefaultToonAdapter(webtoonList as ArrayList<Webtoon>, choose, choose)
            }

            else -> {
                ViewPagerDefaultToonAdapter(webtoonList as ArrayList<Webtoon>, choose, choose)
            }
        }
        /* 참고 : https://velog.io/@king-jungin/Android-%EC%96%91-%EC%98%86%EC%9D%B4-%EB%AF%B8%EB%A6%AC%EB%B3%B4%EC%9D%B4%EB%8A%94-ViewPager2-%EB%A7%8C%EB%93%A4%EA%B8%B0 */

        binding.vpMain.apply {
            adapter = adapterType
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1
        }
    }

    private fun setRecyclerView(
        binding: RecyclerMainItemRecyclerviewBinding,
        choose: Int,
        webtoonList: List<Webtoon>
    ) {
        val context = binding.root.context
        val bannerItems = context.resources.getStringArray(R.array.banner_items)
        val count = 0

        binding.ivAd.visibility = View.GONE

        val adapterType = when (choose) {
            // recycler -1=only ad 0=default, 1=big size, 2=middle size, 3=change container, 4=long size banner
            -1 -> {
                binding.ivAd.visibility = View.VISIBLE
                binding.incluedLayoutBanner.linearContainer.visibility = View.GONE
                binding.incluedLayoutBanner.tvTag.text = bannerItems[count]
                count.inc()
                null
            }
            3 -> {
                binding.constraintDfContainer.apply {
                    setBackgroundColor(Color.BLUE)
                    binding.incluedLayoutBanner.tvTag.text = bannerItems[count]
                }
                count.inc()
                RecyclerDefaultToonAdapter(webtoonList, choose)
            }

            else -> {
                binding.incluedLayoutBanner.tvTag.text = bannerItems[count]
                count.inc()
                RecyclerDefaultToonAdapter(webtoonList, choose)
            }
        }

        adapterType?.let {
            // setLayoutManager.recycleChildrenOnDetach = true // 아이템 뿐 아니라 아이템 내의 View도 모두 재사용 됨.
            binding.rvMainDefaultList.apply {
                adapter = adapterType
                layoutManager = LinearLayoutManager(
                    binding.rvMainDefaultList.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                setItemViewCacheSize(5) // 아이템 화면 밖으로 사라져도, n만큼의 항목을 계속 유지 -> 캐싱해두는것. onBind를 실행하지 않아도 됨.
                setHasFixedSize(true)
            }
        }
    }
}