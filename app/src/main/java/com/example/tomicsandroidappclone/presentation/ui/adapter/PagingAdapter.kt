package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class PagingAdapter : PagingDataAdapter<Webtoon, PagingAdapter.PagingViewHolder>(diffCallback) {

    inner class PagingViewHolder(
        private val binding: DefaultToonItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(webtoon: Webtoon) {
            if (webtoon.additional.up) {
                Glide.with(binding.root.context)
                    .load(webtoon.img)
                    .placeholder(R.drawable.icon_not_founded)
                    .into(binding.ivToonImg)
                binding.tvToonTitle.text = webtoon.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingViewHolder(
            DefaultToonItemsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Webtoon>() {
            override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem == newItem
            }
        }
    }
}


