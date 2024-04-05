package com.example.tomicsandroidappclone.presentation.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemDefaultToonListBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemPopularityToonListBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemTopToonListBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class MainPageAdapter (
    private val webtoon: ArrayList<Webtoon>
) : RecyclerView.Adapter<MainPageAdapter.Viewholder>() {

    companion object {
        private const val TYPE_TOP_TOON = 0
        private const val TYPE_POPULARITY_TOON = 1
        private const val TYPE_DEFAULT_TOON_LIST = 2
    }

    abstract class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position:Int)
    }

    inner class TopToonListViewHolder(private val binding: RecyclerMainItemTopToonListBinding) :
        Viewholder(binding.root) {
        override fun bind(position: Int) {
            setTopViewPager(binding, position)
        }
    }

    inner class PopularityToonListViewHolder(private val binding: RecyclerMainItemPopularityToonListBinding) :
        Viewholder(binding.root) {
        override fun bind(position: Int) {
            setPopularityList(binding)
        }
    }

    inner class DefaultToonListViewHolder(private val binding: RecyclerMainItemDefaultToonListBinding) :
    Viewholder(binding.root){
        override fun bind(position: Int) {
            setDefaultToonList(binding)
        }
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Log.d("TAG", "MainPage onBindViewHolder: 실행")
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return when (viewType) {
            TYPE_TOP_TOON -> {
                val binding = RecyclerMainItemTopToonListBinding.inflate(LayoutInflater.from(parent.context))
                TopToonListViewHolder(binding)
            }

            TYPE_POPULARITY_TOON -> {
                val binding = RecyclerMainItemPopularityToonListBinding.inflate(LayoutInflater.from(parent.context))
                PopularityToonListViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_TOP_TOON
            1 -> TYPE_POPULARITY_TOON
            2 -> TYPE_DEFAULT_TOON_LIST
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        Log.d("Adapter getItem TAG", "MainPage getItemCount 실행")
        return 3
    }

    private fun setTopViewPager(binding: RecyclerMainItemTopToonListBinding, position:Int) {
        val mainTopSlideAdapter = TopSlideAdapter(webtoon)

        binding.rvMain.apply {
            this.adapter = mainTopSlideAdapter
            if(position == itemCount - 1) smoothScrollToPosition(0)
            layoutManager = LinearLayoutManager(
                binding.rvMain.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
            setHasFixedSize(true) // 리사이클러뷰 크기의 고정을 알림. -> 비용 줄어듬
        }
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
            onFlingListener?.let{error ->
                Toast.makeText(binding.root.context, "$error", Toast.LENGTH_SHORT)
            }
            setHasFixedSize(true)
        }
    }

    private fun setDefaultToonList(binding: RecyclerMainItemDefaultToonListBinding) {
        val defaultToonListAdapter = DefaultToonListAdapter(webtoon)
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