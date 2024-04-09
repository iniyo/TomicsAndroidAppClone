package com.example.tomicsandroidappclone.presentation.adapter

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsSubBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyLogChecker
import kotlin.math.min
import kotlin.random.Random

class SubPopularityItemAdapter(
    private val webtoonList: List<Webtoon>
) : ListAdapter<Webtoon, SubPopularityItemAdapter.ViewHolder>(ItemCallback()) {
    private var currentPage = 0
    private val itemsPerPage = 5
    inner class ViewHolder(val binding: PopularityToonItemsSubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon, position: Int) {
            val adjustpostion = position + currentPage * itemsPerPage
            MyLogChecker().logCheck("ivwebtoonclicklistner")
            binding.llTopContainer.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                binding.root.context.startActivity(intent)
            }

            Glide.with(binding.root.context)
                .load(webtoon.img)
                .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                .into(binding.ivPopularity)

            binding.tvToonRank.text = adjustpostion.inc().toString()
            binding.tvToonName.text = webtoon.title

            MyLogChecker().logCheck("ivwebtoonclicklistner", webtoon)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = PopularityToonItemsSubBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoonList[position], position)
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

    fun updatePage(page: Int) {
        currentPage = page
    }

    fun getPage(): Int {
        return webtoonList.size - currentPage * itemsPerPage
    }
    override fun getItemCount(): Int {
        val remainingItems: Int = webtoonList.size - currentPage * itemsPerPage
        return min(remainingItems.toDouble(), itemsPerPage.toDouble()).toInt()
    }
}