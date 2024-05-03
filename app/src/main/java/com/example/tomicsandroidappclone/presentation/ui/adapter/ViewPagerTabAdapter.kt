package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.DefaultToonItemsBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper

// 참고 사이트 : https://www.youtube.com/watch?v=ySPbJ7OCVEE
// https://velog.io/@dlwpdlf147/Android-Paging3-%EC%98%88%EC%A0%9C%EB%A1%9C-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0

// PagingSource : 네트워크나 DB로부터 데이터를 로드하는 클래스
// 언제 PagingSource로부터 얼만큼 PagingData를 가져올지 설정하는 클래스


class ViewPagerTabAdapter(
    private val checkType: Int
) : PagingDataAdapter<Webtoon, ViewPagerTabAdapter.ViewHolder>(diffCallback) {
    private lateinit var mapper: MyGraphicMapper
    /*init {
        setHasStableIds(true) // 각 아이템 position에 지정된 id를 기준으로 상황에 따라 bind호출을 제외.
    }*/
    private val countAttached = 0
    private val countDetached = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DefaultToonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.bind(item)
        }
    }
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        Log.d("TAG", "onViewAttachedToWindow: ${countAttached.inc()} ")
    }
    inner class ViewHolder(val binding: DefaultToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            binding.apply {
                /*mapper = MyGraphicMapper()

                var dpHeight = 200
                var dpWidth = 170

                if(checkType == 1){
                    // default
                    ivToonImg.layoutParams.height = mapper.dp2px(dpHeight)
                    rlDefaultToonSize.layoutParams.width = mapper.dp2px(dpWidth)
                } else {
                    // big size
                    dpHeight = 300
                    dpWidth = 260
                    ivToonImg.layoutParams.height = mapper.dp2px(dpHeight)
                    rlDefaultToonSize.layoutParams.width = mapper.dp2px(dpWidth)
                }*/
                if (webtoon.additional.up) {
                    Glide.with(root.context)
                        .load(webtoon.img)
                        .skipMemoryCache(false) // cache 사용 x
                        .centerInside()
                        .placeholder(R.drawable.icon_not_founded)
                        .into(ivToonImg)
                    tvToonTitle.text = webtoon.title
                }
            }
        }
    }
    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        Log.d("TAG", "onViewDetachedFromWindow: ${countDetached.inc()} ")
        Glide.with(holder.itemView.context)
            .clear(holder.itemView)
    }
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Webtoon>() {
            override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem._id === newItem._id // Referential equality
            }

            override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem == newItem // == Structural equality
            }
        }
    }
}