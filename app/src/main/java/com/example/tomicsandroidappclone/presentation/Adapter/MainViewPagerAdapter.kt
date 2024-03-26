package com.example.tomicsandroidappclone.presentation.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.databinding.ViewpagerItemBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class MainViewPagerAdapter : ListAdapter<Webtoon, MainViewPagerAdapter.ViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // position에 따른 현재 설정된 리스트의 데이터 참조
        holder.bind(currentList[position]) // LiveData 객체이며 데이터 목록이 변경되면 업데이트 됨.
    }

    inner class ViewHolder(private val binding: ViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            Glide.with(binding.ivWebtoon.context).load(webtoon.img.toUri()).into(binding.ivWebtoon)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    class ItemCallback : DiffUtil.ItemCallback<Webtoon>() {
        override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem == newItem
        }
    }
}