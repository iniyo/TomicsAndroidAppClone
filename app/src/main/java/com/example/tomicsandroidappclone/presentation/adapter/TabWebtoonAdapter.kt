package com.example.tomicsandroidappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class TabWebtoonAdapter(
    private val webtoon: List<Webtoon>
): ListAdapter<Webtoon, TabWebtoonAdapter.ViewHolder>(TabWebtoonAdapter.ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabWebtoonAdapter.ViewHolder {
        val binding = DefaultToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabWebtoonAdapter.ViewHolder, position: Int) {
        holder.bind(webtoon[position])
    }

    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {

            if (webtoon.additional.up) {
                Glide.with(binding.root.context)
                    .load(webtoon.img)
                    .placeholder(R.drawable.icon_not_founded) // image 로드를 못 했을 경우
                    .into(binding.ivToonImg)
                binding.tvToonTitle.text = webtoon.title
            }
        }
    }

    override fun getItemCount(): Int {
        return webtoon.size
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