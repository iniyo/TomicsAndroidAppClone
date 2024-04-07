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
    private val webtoonList: ArrayList<Webtoon>,
    private val toonType: Int
) : ListAdapter<Webtoon, DefaultToonListAdapter.ViewHolder>(ItemCallback()) {
    private lateinit var mapper: MyGraphicMapper

    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            mapper = MyGraphicMapper()
            Log.d("size TAG", "size: $toonType ")
            // default
            if (toonType == 0) {
                val dpHeight = 1100
                val dpWidth = 700
                binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                binding.ivToonImg.layoutParams.width = mapper.px2dp(dpWidth)
            }
            // oversize
            else if (toonType == 1) {
                val dpHeight = 1600
                val dpWidth = 1200
                binding.ivToonImg.layoutParams.height = mapper.px2dp(dpHeight)
                binding.ivToonImg.layoutParams.width = mapper.px2dp(dpWidth)
            }

            if (webtoon.additional.up) {
                Glide.with(binding.root.context)
                    .load(webtoon.img)
                    .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                    /*.apply(RequestOptions().override(mapper.px2dp(dpWidth), mapper.px2dp(dpHeight)))*/
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

    fun updateData(newWebtoonList: ArrayList<Webtoon>) {
        webtoonList.clear()
        webtoonList.addAll(newWebtoonList)
        notifyDataSetChanged() // 전체 데이터 갱신시 사용
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

    fun setDefaultToonList() {

    }

}