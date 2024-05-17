package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper

class ViewPagerTabAdapter(
    private val checkType: Int
) : PagingDataAdapter<Webtoon, ViewPagerTabAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DefaultToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        } else {
            Log.d("TAG", "item이 비어있음. ")
        }
    }

    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            binding.apply {
                val mapper = MyGraphicMapper()
                val screenWidth = mapper.getScreenWidth(root.context)

                val spanCount = when (checkType) {
                    0 -> 1
                    1 -> 3
                    else -> 2
                }

                val dpWidth = screenWidth / spanCount

                if (checkType == 1) {
                    ivToonImg.layoutParams.height = (dpWidth * 1.1).toInt()
                    rlDefaultToonSize.layoutParams.width = dpWidth
                } else {
                    ivToonImg.layoutParams.height = (dpWidth * 1.4).toInt()
                    rlDefaultToonSize.layoutParams.width = dpWidth
                }

                Glide.with(root.context)
                    .load(webtoon.img)
                    .centerInside()
                    .error(R.drawable.ic_error)
                    .fallback(R.drawable.ic_not_founded)
                    .placeholder(R.drawable.ic_loading)
                    .into(ivToonImg)
                tvToonTitle.text = webtoon.title

                ivToonImg.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                    root.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Webtoon>() {
            override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem == newItem
            }
        }
    }
}
