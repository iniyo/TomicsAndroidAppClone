package com.example.tomicsandroidappclone.presentation.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class DefaultToonListAdapter(
    private val webtoonList: ArrayList<Webtoon>
) : ListAdapter<Webtoon, DefaultToonListAdapter.ViewHolder>(ItemCallback()) {

    inner class ViewHolder(val binding: PopularityToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            if(webtoon.additional.up){
                Glide.with(binding.root.context)
                    .load(webtoon.img)
                    .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                    .into(binding.ivPopularity)
                binding.tvTag1.text = "복수"
                binding.tvTag2.text = "먼치킨"
                binding.tvTag3.text = "생존"
                binding.tvToonName.text = webtoon.title
                binding.tvToonRank.text = position.inc().toString()
            }
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
        holder.bind(webtoonList[position])
    }
    fun updateData(newWebtoonList: ArrayList<Webtoon>) {
        webtoonList.clear()
        webtoonList.addAll(newWebtoonList)
        notifyDataSetChanged() // 전체 데이터 갱신시 사용
    }
    class ItemCallback : DiffUtil.ItemCallback<Webtoon>() {
        override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    private fun initPopularityList(binding: PopularityToonItemsBinding) {
        val popularityToonAdapter = SubPopularityItemAdapter(webtoonList)

        binding.rvPopularityListSubItems.apply {
            this.adapter = popularityToonAdapter
            layoutManager = LinearLayoutManager(
                binding.rvPopularityListSubItems.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}