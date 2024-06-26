package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.TopToonItemsBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyGraphicMapper

class ViewPagerTopSlideAdapter(
    private val imgList: List<Int>
) : ListAdapter<Webtoon, ViewPagerTopSlideAdapter.ViewHolder>(diffCallback) {
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
        holder.bind(imgList[position % imgList.size])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // RecyclerView가 연결되었을 때 스크롤 위치를 가운데로 이동
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
    }


    // 참고 사이트 :
    class ViewHolder(private val binding: TopToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(img: Int) {
            binding.apply {
                val mapper = MyGraphicMapper()
                val screenWidth = mapper.getScreenWidth(root.context)

                val imageHeight = (screenWidth * 1.2).toInt()

                Glide.with(root.context)
                    .load(img)
                    .skipMemoryCache(false) // cache 사용 x, 특별한 경우(데이터가 워낙 많은 경우)에만 사용.

                    .placeholder(R.drawable.ic_launcher_foreground) // image 로드를 못 했을 경우
                    .into(ivWebtoon)
                ivWebtoon.layoutParams.height = imageHeight
                ivWebtoon.layoutParams.width = screenWidth

                /*ivWebtoon.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webtoon.url))
                    root.context.startActivity(intent)
                }*/
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Glide.with(holder.itemView.context)
            .clear(holder.itemView)
    }

    // ListAdapter의 경우 아이템 개수를 컨트롤 하지 않아도 되지만 infinite scroll을 위해 getItemCount override
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Webtoon>() {
            override fun areItemsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem.id === newItem.id // Referential equality
            }

            override fun areContentsTheSame(oldItem: Webtoon, newItem: Webtoon): Boolean {
                return oldItem == newItem // == Structural equality
            }
        }
    }
}
