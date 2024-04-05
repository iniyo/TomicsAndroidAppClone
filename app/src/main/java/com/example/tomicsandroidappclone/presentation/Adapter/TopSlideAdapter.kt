package com.example.tomicsandroidappclone.presentation.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.TopToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class TopSlideAdapter(
    private val webtoonList: ArrayList<Webtoon>
) : ListAdapter<Webtoon, TopSlideAdapter.ViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TopToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoonList[position])
    }

    inner class ViewHolder(private val binding: TopToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            Log.d("TAG", "ViewPagerAdapter bind 실행")
            Log.d("TAG", "ViewPagerAdapter bind 데이터 체크 : " + webtoon.img)

            Glide.with(binding.root.context)
                .load(webtoon.img)
                .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                .into(binding.ivWebtoon)
            Log.d("TAG", "bind: 이미지")
        }
    }

    override fun getItemCount(): Int {
        Log.d("Adapter getItem TAG", "ViewPage getItemCount 실행")
        return 7
    }
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

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
