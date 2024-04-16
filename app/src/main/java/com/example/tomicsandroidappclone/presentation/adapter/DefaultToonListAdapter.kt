package com.example.tomicsandroidappclone.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper


class DefaultToonListAdapter(
    private val webtoonList: List<Webtoon>,
    private val toonType: Int
) : ListAdapter<Webtoon, DefaultToonListAdapter.ViewHolder>(ItemCallback()) {
    private lateinit var mapper: MyGraphicMapper

    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            mapper = MyGraphicMapper()
            Log.d("size TAG", "size: $toonType ")

            when (toonType) {
                // default
                1 -> {
                    val dpHeight = 1100
                    val dpWidth = 900

                    binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                    binding.rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)
                }
                // oversize
                2 -> {
                    val dpHeight = 1600
                    val dpWidth = 1300
                    binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                    binding.rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)
                }
            }

            if (webtoon.additional.up) {
                Glide.with(binding.root.context)
                    .load(webtoon.img)
                    .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                    .into(binding.ivToonImg)
                binding.tvToonTitle.text = webtoon.title
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = DefaultToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoonList[position])
    }

    class ItemCallback : DiffUtil.ItemCallback<Webtoon>() {
        override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
            return oldItem == newItem
        }
    }
    override fun getItemCount(): Int = webtoonList.size
}