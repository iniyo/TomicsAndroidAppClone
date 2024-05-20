package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerItemNoticeBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon

class MainRecyclerAdapter(
    private val webtoonData: ArrayList<Webtoon>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypes = listOf(
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.BIG_SIZE_RECYCLER,
        ViewType.MIDDLE_SIZE_RECYCLER,
        ViewType.AD_RECYCLER,
        ViewType.TAG_TO_DEFAULT_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.AD_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.AD_RECYCLER,
        ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.DEFAULT_SIZE_RECYCLER,
        ViewType.LONG_SIZE_BANNER,
        ViewType.NOTICE
    )

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    enum class ViewType {
        DEFAULT_SIZE_RECYCLER, BIG_SIZE_RECYCLER, AD_RECYCLER, LONG_SIZE_BANNER, MIDDLE_SIZE_RECYCLER, TAG_TO_DEFAULT_RECYCLER, DEFAULT_SIZE_TO_SALE_RECYCLER, NOTICE
    }

    inner class RecyclerViewHolder(private val binding: RecyclerMainItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val viewType = getItemViewType(position)
            val num = when (ViewType.entries[viewType]) {
                ViewType.DEFAULT_SIZE_RECYCLER -> 0
                ViewType.BIG_SIZE_RECYCLER -> 1
                ViewType.MIDDLE_SIZE_RECYCLER -> 2
                ViewType.AD_RECYCLER -> 3
                ViewType.LONG_SIZE_BANNER -> 4
                ViewType.TAG_TO_DEFAULT_RECYCLER -> 5
                ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER -> 6
                ViewType.NOTICE -> return // 따로 처리
            }
            setRecyclerView(binding, num)
        }
    }

    inner class NoticeViewHolder(binding: RecyclerItemNoticeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "MainRecycler onBindViewHolder: 실행")
        if (holder is RecyclerViewHolder) {
            holder.bind(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "MainRecycler onCreateViewHolder: $viewType")
        return when (ViewType.values()[viewType]) {
            ViewType.NOTICE -> {
                val binding = RecyclerItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NoticeViewHolder(binding)
            }
            else -> {
                val binding = RecyclerMainItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RecyclerViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = viewTypes[position].ordinal
    override fun getItemCount(): Int = viewTypes.size

    private fun setRecyclerView(
        binding: RecyclerMainItemRecyclerviewBinding,
        choose: Int
    ) {
        binding.apply {
            val bannerItems = root.context.resources.getStringArray(R.array.banner_items)
            ivAd.visibility = View.GONE

            val adapterType = when (choose) {
                -1 -> {
                    ivAd.visibility = View.VISIBLE
                    incluedLayoutBanner.linearContainer.visibility = View.GONE
                    incluedLayoutBanner.tvTag.text = bannerItems[choose]
                    null
                }

                3 -> {
                    constraintDfContainer.apply {
                        setBackgroundColor(Color.BLUE)
                        incluedLayoutBanner.tvTag.text = bannerItems[choose]
                    }
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }

                else -> {
                    incluedLayoutBanner.tvTag.text = bannerItems[choose]
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }
            }

            adapterType?.let {
                rvMainDefaultList.apply {
                    adapter = adapterType
                    layoutManager = LinearLayoutManager(
                        rvMainDefaultList.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ).apply {
                        recycleChildrenOnDetach = true
                    }
                    setItemViewCacheSize(viewTypes.size)
                    setHasFixedSize(true)
                }
            }
        }
    }
}
