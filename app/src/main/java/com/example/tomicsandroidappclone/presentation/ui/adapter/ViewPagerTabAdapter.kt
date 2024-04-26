package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class ViewPagerTabAdapter(
    private val webtoon: MutableList<Webtoon> = mutableListOf() // MutableList로 변경
) : RecyclerView.Adapter<ViewPagerTabAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DefaultToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoon[position])
    }

    class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    override fun getItemCount(): Int {
        return webtoon.size
    }
}