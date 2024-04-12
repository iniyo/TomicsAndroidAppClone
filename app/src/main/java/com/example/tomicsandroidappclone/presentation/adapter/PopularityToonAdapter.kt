package com.example.tomicsandroidappclone.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class PopularityToonAdapter(
    private val webtoonList: List<Webtoon>,
    private val checkType: Int
) : ListAdapter<Webtoon, PopularityToonAdapter.ViewHolder>(ItemCallback()) {
    private var count = 0
    private var number = 5
    inner class ViewHolder(val binding: PopularityToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            Log.d("TAG", "MainPopularityToonAdapter bind 실행")
            Log.d("TAG", "MainPopularityToonAdapter bind 데이터 체크 : " + webtoon.img)

            initPopularityList(binding)
            Log.d("TAG", "MainPopularityToonAdapter bind: 이미지")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = PopularityToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoonList[position % (webtoonList.size / 5)])
    }
    /*  fun updateData(newWebtoonList: ArrayList<Webtoon>) {
              webtoon.clear()
              webtoon.addAll(newWebtoonList)
              notifyDataSetChanged() // 전체 데이터 갱신시 사용
          }*/
    class ItemCallback : DiffUtil.ItemCallback<Webtoon>() {
        override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    private fun initPopularityList(binding: PopularityToonItemsBinding) {

        val adapterType: RecyclerView.Adapter<*>
        val layoutManagerType = when (checkType) {
            -1 -> {
                adapterType = SubPopularityItemAdapter(webtoonList.take(number))
                LinearLayoutManager(
                    binding.rvPopularityListSubItems.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
            0 -> {
                adapterType = TabWebtoonAdapter(webtoonList)
                GridLayoutManager(binding.rvPopularityListSubItems.context, 3)
            }
            else -> {
                adapterType = TabWebtoonAdapter(webtoonList)
                GridLayoutManager(binding.rvPopularityListSubItems.context, 2)
            }
        }


        Log.d("initPopularityList TAG", "initPopularityList: $count")
        binding.rvPopularityListSubItems.apply {
            adapter = adapterType
            layoutManager = layoutManagerType
            if(checkType == -1){
                (adapter as SubPopularityItemAdapter).updatePage(count)
                val page = (adapter as SubPopularityItemAdapter).getPage()
                if(page != 10) {
                    count.inc()
                    Log.d("TAG", "count: $count")
                }else{
                    count = 0
                }
            }
        }
    }
}