package com.example.tomicsandroidappclone.presentation.ui.adapter

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


class RecyclerDefaultToonAdapter(
    private val webtoonList: ArrayList<Webtoon>,
    private val toonType: Int
) : ListAdapter<Webtoon, RecyclerDefaultToonAdapter.ViewHolder>(ItemCallback()) {
    private lateinit var mapper: MyGraphicMapper

    init {
        setHasStableIds(true) // 각 아이템 position에 지정된 id를 기준으로 상황에 따라 bind호출을 제외.
    }

    override fun getItemId(position: Int): Long = position.toLong()
    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            mapper = MyGraphicMapper()
            Log.d("size TAG", "size: $toonType ")
            var dpHeight = 1100
            var dpWidth = 900
            when (toonType) {
                // default
                0 -> {
                    binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                    binding.rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)
                }
                // big size
                1 -> {
                    dpHeight = 1700
                    dpWidth = 1400
                    binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                    binding.rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)
                }
                // middle size
                2 -> {
                    dpHeight = 1300
                    dpWidth = 1400
                    binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                    binding.rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)
                }
                // foot - ad long size banner
                3 -> {
                    dpHeight = 1100
                    dpWidth = 3000
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
        webtoonList[position].let {
            holder.bind(it)
        }
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