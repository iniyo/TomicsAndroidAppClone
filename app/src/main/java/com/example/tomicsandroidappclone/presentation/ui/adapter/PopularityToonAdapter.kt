package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class PopularityToonAdapter(
    private val webtoonList: ArrayList<Webtoon>,
    private val checkType: Int
) : RecyclerView.Adapter<PopularityToonAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PopularityToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.rvPopularityListSubItems.apply {
                layoutManager = when (checkType) {
                    0 -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    1 -> GridLayoutManager(context, 3)
                    else -> GridLayoutManager(context, 2)
                }
                adapter = when (checkType) {
                    0 -> SubPopularityItemAdapter(webtoonList)
                    else -> TabWebtoonAdapter(webtoonList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularityToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // For infinite scrolling
    override fun getItemId(position: Int): Long = position.toLong() // Stable IDs
}