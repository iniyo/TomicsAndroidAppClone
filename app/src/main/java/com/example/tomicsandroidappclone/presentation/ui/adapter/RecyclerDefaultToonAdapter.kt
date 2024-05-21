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
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper

class RecyclerDefaultToonAdapter(
    private val webtoonList: ArrayList<Webtoon>,
    private val toonType: Int
) : ListAdapter<Webtoon, RecyclerDefaultToonAdapter.ViewHolder>(ItemCallback()) {
    private lateinit var mapper: MyGraphicMapper
    private val adImages = listOf(
        R.drawable.ic_footer_ad_1,
        R.drawable.ic_footer_ad_2,
        R.drawable.ic_footer_ad_3,
        R.drawable.ic_footer_ad_4
    )
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon, adImageRes: Int?) {
            mapper = MyGraphicMapper()
            Log.d("size TAG", "size: $toonType ")
            var dpHeight = 800
            var dpWidth = 600
            binding.apply {
                when (toonType) {
                    1 -> {
                        dpHeight = 1100
                        dpWidth = 900
                    }
                    2 -> {
                        dpHeight = 900
                        dpWidth = 900
                    }
                    4 -> {
                        dpHeight = 900
                        dpWidth = 630
                    }
                    6 -> {
                        rlDefaultToonSize.removeView(tvToonTitle)
                        rlDefaultToonSize.removeView(tvEpisodes)
                        dpHeight = 800
                        dpWidth = 1300
                    }
                    else -> {
                        dpHeight = 800
                        dpWidth = 600
                    }
                }
                ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                rlDefaultToonSize.layoutParams.width = mapper.px2dp(dpWidth)

                if (webtoon.additional.up && toonType != 6) {
                    Glide.with(root.context)
                        .load(webtoon.img)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(ivToonImg)
                    tvToonTitle.text = webtoon.title
                } else if (toonType == 6 && adImageRes != null) {
                    Glide.with(root.context)
                        .load(adImageRes)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(ivToonImg)
                    tvToonTitle.text = webtoon.title
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DefaultToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val webtoon = webtoonList[position]
        if (toonType == 6 && position < adImages.size) {
            holder.bind(webtoon, adImages[position])
        } else {
            holder.bind(webtoon, null)
        }
    }

    override fun getItemCount(): Int {
        return if (toonType == 6) {
            minOf(webtoonList.size, adImages.size)
        } else {
            webtoonList.size
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

    override fun onViewDetachedFromWindow(holder: RecyclerDefaultToonAdapter.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Glide.with(holder.itemView.context).clear(holder.itemView)
    }
}
