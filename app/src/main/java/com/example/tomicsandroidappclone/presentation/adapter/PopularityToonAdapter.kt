package com.example.tomicsandroidappclone.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.databinding.PopularityToonItemsBinding
import com.example.tomicsandroidappclone.domain.entity.Webtoon

class PopularityToonAdapter(
    private val webtoonList: ArrayList<Webtoon>,
    private val checkType: Int
) : RecyclerView.Adapter<PopularityToonAdapter.ViewHolder>() {
    private val viewPool: RecyclerView.RecycledViewPool =
        RecyclerView.RecycledViewPool() // 동일한 스레드에서만 사용해야 함.

    // 아이템 ID를 저장하는 Map
    private val itemIds = mutableMapOf<Int, Long>()


    init {
        setHasStableIds(true)
    }

    inner class ViewHolder(val binding: PopularityToonItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(webtoon: Webtoon) {
            initPopularityList(binding)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = PopularityToonItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        // 아이템 ID가 Map에 존재하지 않으면 생성
        if (!itemIds.containsKey(position)) {
            val itemId = position.toLong() // 아이템 데이터베이스 ID 사용
            itemIds[position] = itemId
        }

        // Map에 저장된 아이템 ID 반환
        return itemIds[position]!!
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // RecyclerView가 연결되었을 때 스크롤 위치를 가운데로 이동
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItemId(position)
        holder.itemView.tag = position
        holder.bind(webtoonList[position % webtoonList.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    private fun initPopularityList(binding: PopularityToonItemsBinding) {

        val bindingConfig = binding.rvPopularityListSubItems

        val adapterType: RecyclerView.Adapter<*>
        val layoutManagerType = when (checkType) {
            -1 -> {
                adapterType = SubPopularityItemAdapter(webtoonList)
                LinearLayoutManager(
                    bindingConfig.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            0 -> {
                adapterType = TabWebtoonAdapter(webtoonList)
                GridLayoutManager(bindingConfig.context, 3)
            }

            else -> {
                adapterType = TabWebtoonAdapter(webtoonList)
                GridLayoutManager(bindingConfig.context, 2)
            }
        }

        bindingConfig.apply {
            adapter = adapterType
            layoutManager = layoutManagerType

            //setRecycledViewPool(viewPool)
        }
    }
}