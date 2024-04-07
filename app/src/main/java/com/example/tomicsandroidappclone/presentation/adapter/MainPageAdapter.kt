package com.example.tomicsandroidappclone.presentation.adapter

import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemDefaultToonListBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemPopularityToonListBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemTopToonListBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler
import kotlin.math.ceil


class MainPageAdapter (
    private val webtoon: ArrayList<Webtoon>
) : RecyclerView.Adapter<MainPageAdapter.Viewholder>() {
    enum class ViewType{
        TYPE_TOP_TOON, TYPE_POPULARITY_TOON, TYPE_DEFAULT_TOON_LIST, OTHER_TYPE
    }
    data class MainItem(val viewType: ViewType, val webtoonData: ArrayList<Webtoon>?, val choose: Int)

    val itemList = listOf(
        MainItem(ViewType.TYPE_TOP_TOON, webtoon, 0),
        MainItem(ViewType.TYPE_POPULARITY_TOON, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 1),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 1),
        MainItem(ViewType.OTHER_TYPE, webtoon, 1),
    )

    abstract class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position:Int)
    }

    inner class TopToonListViewHolder(private val binding: RecyclerMainItemTopToonListBinding) :
        Viewholder(binding.root) {
        override fun bind(position: Int) {}
    }

    inner class PopularityToonListViewHolder(private val binding: RecyclerMainItemPopularityToonListBinding) :
        Viewholder(binding.root) {
        override fun bind(position: Int) {

        }
    }

    inner class DefaultToonListViewHolder(private val binding: RecyclerMainItemDefaultToonListBinding) :
    Viewholder(binding.root){
        override fun bind(position: Int) {
            itemList[position].choose
            setDefaultToonList(binding, itemList[position].choose)
        }
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Log.d("TAG", "MainPage onBindViewHolder: 실행")
        val id = getItemId(position)
        Log.d("TAG", "MainPage onBindViewHolder: $id")

        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        Log.d("TAG", "MainPage onCreateViewHolder: 실행")
        return when (ViewType.entries[viewType]) {
            ViewType.TYPE_TOP_TOON -> {
                val binding = RecyclerMainItemTopToonListBinding.inflate(LayoutInflater.from(parent.context))
                val holder = TopToonListViewHolder(binding)
                setTopViewPager(binding)
                holder
            }
            ViewType.TYPE_POPULARITY_TOON -> {
                val binding = RecyclerMainItemPopularityToonListBinding.inflate(LayoutInflater.from(parent.context))
                PopularityToonListViewHolder(binding)
                val holder = PopularityToonListViewHolder(binding)
                setPopularityList(binding)
                holder
            }
            ViewType.TYPE_DEFAULT_TOON_LIST -> {
                val binding = RecyclerMainItemDefaultToonListBinding.inflate(LayoutInflater.from(parent.context))
                DefaultToonListViewHolder(binding)
            }
            // 미정
            ViewType.OTHER_TYPE -> {
                val binding = RecyclerMainItemDefaultToonListBinding.inflate(LayoutInflater.from(parent.context))
                DefaultToonListViewHolder(binding)
            }
            /*-> throw IllegalArgumentException("Invalid view type")*/
        }
    }

    // entries : 열거형의 모든 값을 배열로 반환
    // ordinal : entries[position]의 순서 반환 -> 특정(position) 배열 값 반환
    // Int 자료형으로 ViewType.entries[position].ordinal값을 반환
    override fun getItemViewType(position: Int): Int {
        Log.d("TAG", "getItemViewType 실행")
        return itemList[position].viewType.ordinal
    }

    override fun getItemCount(): Int = itemList.size

    private fun setTopViewPager(binding: RecyclerMainItemTopToonListBinding) {
        val webtoonList = webtoon.take(7)
        val mainTopSlideAdapter = TopSlideAdapter(webtoonList as ArrayList<Webtoon>)
        binding.vpMain.adapter = mainTopSlideAdapter
        binding.vpMain.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        var bannerPosition = 0
        binding.vpMain.setCurrentItem(bannerPosition, true)
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            binding.vpMain.setCurrentItem(++bannerPosition, true)
        }
        binding.vpMain.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d("TAG", "onPageScrollStateChanged: $state")
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(3000)
                            binding.vpMain.setCurrentItem(++bannerPosition, true)
                            // 이전 페이지 색상 변경
                            val previousPosition = bannerPosition - 1
                            if (previousPosition >= 0 && previousPosition < binding.llTopSlideObserver.childCount) {
                                binding.llTopSlideObserver.getChildAt(previousPosition).setBackgroundColor(111111)
                            }

                            // 다음 페이지 색상 변경
                            binding.llTopSlideObserver.getChildAt(bannerPosition % 7).setBackgroundColor(777777)
                        }
                    }
                }
            }
        })
    }

    private fun setPopularityList(binding: RecyclerMainItemPopularityToonListBinding) {
        val popularityToonAdapter = PopularityToonAdapter(webtoon)

        binding.rvPopularityList.apply {
            this.adapter = popularityToonAdapter
            layoutManager = LinearLayoutManager(
                binding.rvPopularityList.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
            setHasFixedSize(true)
        }
    }

    private fun setDefaultToonList(binding: RecyclerMainItemDefaultToonListBinding, choose: Int) {
        val defaultToonListAdapter = DefaultToonListAdapter(webtoon, choose)
        when(choose){
            0 -> binding.tvSeeMore.visibility = View.GONE
            1 -> binding.tvSeeMore.visibility = View.VISIBLE
            2 -> binding.tvSeeMore.visibility = View.VISIBLE
        }

        binding.rvMainDefaultList.apply {
            this.adapter = defaultToonListAdapter
            layoutManager = LinearLayoutManager(
                binding.rvMainDefaultList.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }
    }
}