package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.TopToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class ViewPagerTopSlideAdapter(
    private val webtoonList: List<Webtoon>
) : RecyclerView.Adapter<ViewPagerTopSlideAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TopToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(webtoonList[position % webtoonList.size])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // RecyclerView가 연결되었을 때 스크롤 위치를 가운데로 이동
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
    }

    class ViewHolder(private val binding: TopToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {

            Log.d("TAG", "ViewPagerAdapter bind 데이터 체크 : " + webtoon.img)
            Glide.with(binding.root.context)
                .load(webtoon.img)
                .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                .into(binding.ivWebtoon)
            Log.d("TAG", "bind: 이미지")
            binding.ivWebtoon.setOnClickListener {
                Log.d("TAG", "bind - ivwebtoonclicklistner ")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}
