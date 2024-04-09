package com.example.tomicsandroidappclone.presentation.adapter

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemDefaultToonListBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemTopToonListBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.adapter.AutoScrollHandler.AutoScrollHandler
import okhttp3.internal.Util


class MainPageAdapter (
    private val webtoon: List<Webtoon>
) : RecyclerView.Adapter<MainPageAdapter.Viewholder>() {
    private var count = 0
    private val MSG_SCROLL = 1
    enum class ViewType{
        TYPE_TOP_TOON, TYPE_DEFAULT_TOON_LIST, OTHER_TYPE
    }
    data class MainItem(val viewType: ViewType, val webtoonData: List<Webtoon>, val choose: Int)

    val itemList = listOf(
        MainItem(ViewType.TYPE_TOP_TOON, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 0),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 1),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 2),
        MainItem(ViewType.TYPE_DEFAULT_TOON_LIST, webtoon, 2),
        MainItem(ViewType.OTHER_TYPE, webtoon, 1),
    )

    abstract class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position:Int)
    }

    inner class TopToonListViewHolder(private val binding: RecyclerMainItemTopToonListBinding) :
        Viewholder(binding.root) {
        override fun bind(position: Int) {}
    }


    inner class DefaultToonListViewHolder(private val binding: RecyclerMainItemDefaultToonListBinding) :
    Viewholder(binding.root){
        override fun bind(position: Int) {
            itemList[position].choose
            setRecyclerView(binding, itemList[position].choose, webtoon)
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
    override fun getItemViewType(position: Int): Int = itemList[position].viewType.ordinal
    override fun getItemCount(): Int = itemList.size
    private fun setTopViewPager(binding: RecyclerMainItemTopToonListBinding) {
        val webtoonList = webtoon.take(7)
        val mainTopSlideAdapter = TopSlideAdapter(webtoonList as ArrayList<Webtoon>)

        binding.vpMain.apply {
            adapter = mainTopSlideAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

        }
        val myHandler = AutoScrollHandler(binding.vpMain)
        myHandler.startAutoScroll(3000)

        binding.vpMain.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                Log.d("TAG", "onPageScrollStateChanged: $state")

                when(state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Log.d("TAG", "onPageScrollStateChanged: ")
                        myHandler.startAutoScroll(2000)
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        myHandler.stopAutoScroll()
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {

                    }
                }
            }
        })
    }

    private fun setRecyclerView(
        binding: RecyclerMainItemDefaultToonListBinding,
        choose: Int,
        webtoonList: List<Webtoon>
    ) {

        val context = binding.root.context
        val bannerItems = context.resources.getStringArray(R.array.banner_items)
        for(i in bannerItems.indices){
            Log.d("TAG", "bannerItems $bannerItems[i]")
        }


        val adapterType =
            if(choose == 0){
                PopularityToonAdapter(webtoonList.take(30))

            } else {
                DefaultToonListAdapter(webtoonList, choose)
            }

        binding.rvMainDefaultList.apply {
            adapter = adapterType
            layoutManager = LinearLayoutManager(
                binding.rvMainDefaultList.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            if(choose == 0){
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(binding.rvMainDefaultList)
                setHasFixedSize(true)

            } else {
                setHasFixedSize(true)
            }
        }
    }
}