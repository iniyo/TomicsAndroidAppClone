package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsSubBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyLogChecker

class ViewPagerSubListItemsAdapter(
    private val webtoonList: List<Webtoon>
) :  RecyclerView.Adapter<ViewPagerSubListItemsAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: PopularityToonItemsSubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon, position: Int) {

            MyLogChecker().logCheck("ivwebtoonclicklistner")
            binding.llTopContainer.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                binding.root.context.startActivity(intent)
            }
            Glide.with(binding.root.context)
                .load(webtoon.img)
                .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                .into(binding.ivPopularity)
            binding.tvToonRank.text = position.inc().toString()
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
    override fun getItemCount(): Int {
        return 5
    }
}