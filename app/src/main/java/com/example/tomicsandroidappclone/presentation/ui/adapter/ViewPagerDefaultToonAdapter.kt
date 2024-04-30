package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class ViewPagerDefaultToonAdapter(
    private val webtoonList: ArrayList<Webtoon>,
    private val checkType: Int,
    private val getSize: Int
) : RecyclerView.Adapter<ViewPagerDefaultToonAdapter.ViewHolder>() {

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
                    0 -> ViewPagerSubListItemsAdapter(webtoonList)
                    else -> ViewPagerTabAdapter(webtoonList)
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (checkType == 0) {
            recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
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

    /*private fun getPartialList(holder: ViewPagerTabAdapter.ViewHolder): List<Webtoon> {
        val startIndex = holder.adapterPosition * poolSize
        val endIndex = minOf(startIndex + poolSize, webtoon.size)
        if (startIndex >= webtoon.size) {
            return emptyList()
        }
        Log.d("TAG", "from: $startIndex ")
        Log.d("TAG", "to: $endIndex ")
        return webtoon.subList(startIndex, endIndex)
    }*/

    override fun getItemCount(): Int {
        return if (checkType == 0) Int.MAX_VALUE
        else getSize
    }
    override fun getItemId(position: Int): Long = position.toLong()
}