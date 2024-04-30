package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Intent
import android.net.Uri
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
    init {
        setHasStableIds(true) // 각 아이템 position에 지정된 id를 기준으로 상황에 따라 bind호출을 제외.
    }

    override fun getItemId(position: Int): Long = position.toLong()
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


    // 참고 사이트 :
    class ViewHolder(private val binding: TopToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {

            Glide.with(binding.root.context)
                .load(webtoon.img)
                .skipMemoryCache(false) // cache 사용 x, 특별한 경우(데이터가 워낙 많은 경우)에만 사용.
                .centerInside() // 이미지 사이즈만 재조정, 직접 재조정하는 방법이 좀 더 빠름.
                .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                .into(binding.ivWebtoon)

            binding.ivWebtoon.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Glide.with(holder.itemView.context)
            .clear(holder.itemView)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}
