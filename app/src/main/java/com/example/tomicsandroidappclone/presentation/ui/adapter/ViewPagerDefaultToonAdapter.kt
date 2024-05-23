package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.adapter.MyGridSpacingItemDecoration

class ViewPagerDefaultToonAdapter(
    private val checkType: Int,
    private val getSize: Int,
    private var viewPagerTabAdapter: ViewPagerTabAdapter? = null,
    private val webtoonList: ArrayList<Webtoon>? = null,
    private val imgList: List<Int> = emptyList()
) : RecyclerView.Adapter<ViewPagerDefaultToonAdapter.ViewHolder>() {

    // recyclerViewPool
    private val recyclerViewPool = RecyclerView.RecycledViewPool().apply {
        setMaxRecycledViews(2, 100)
    }
    private var adapterDataObserver: RecyclerView.AdapterDataObserver? = null
    init {
        setHasStableIds(true) // 각 아이템 position에 지정된 id를 기준으로 상황에 따라 bind호출을 제외.
    }
    /*class PreCacheLayoutManager(context: Context, private val extraLayoutSpace: Int) :
            LinearLayoutManager(context) {
            @Deprecated("Deprecated in Java")
            override fun getExtraLayoutSpace(state: RecyclerView.State?) = extraLayoutSpace
        }*/
    inner class ViewHolder(val binding: PopularityToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            try {
                binding.rvPopularityListSubItems.apply {
                    layoutManager = when (checkType) {
                        0 -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        1 -> GridLayoutManager(context, 3)
                        else -> GridLayoutManager(context, 2)
                    }.apply{
                        recycleChildrenOnDetach = true
                    }
                    adapter = when (checkType) {
                        0 -> ViewPagerSubListItemsAdapter(webtoonList!!, imgList)
                        else -> viewPagerTabAdapter
                    }
                    if(checkType != 0){
                        addItemDecoration(MyGridSpacingItemDecoration(3, spacing = 10))
                    }
                    setRecycledViewPool(recyclerViewPool)
                }.scrollToPosition(0) // 데이터 로드 후 최상단으로 위치
            }catch (e:Exception){
                Log.e("TAG", "ViewPagerDefaultToonAdapter bind: ${e.message}")
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        Log.d("TAG", "onAttachedToRecyclerView: ")
        if (checkType == 0) {
            recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
        }
        adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                recyclerView.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView.scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                recyclerView.scrollToPosition(0)
            }
        }.also {
            registerAdapterDataObserver(it)
        }
    }
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        Log.d("TAG", "onDetachedFromRecyclerView: ")
        adapterDataObserver?.let {
            unregisterAdapterDataObserver(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularityToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return if (checkType == 0) Int.MAX_VALUE
        else getSize
    }

    override fun getItemId(position: Int): Long = position.toLong()
}