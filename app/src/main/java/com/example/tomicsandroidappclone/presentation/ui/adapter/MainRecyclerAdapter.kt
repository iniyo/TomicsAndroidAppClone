package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon

// 참고사이트 : https://gift123.tistory.com/67 - 중요
// https://ppizil.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-Recyclerview-%EC%A0%9C%EB%8C%80%EB%A1%9C-%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90
class MainRecyclerAdapter(
    private val webtoonData: ArrayList<Webtoon>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var count = 0

    /*private val recyclerViewPool0 = RecyclerView.RecycledViewPool().apply {
        setMaxRecycledViews(0, 20)
    }*/

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
        ViewType.LONG_SIZE_BANNER
    )

    init {
        setHasStableIds(true) // 각 아이템 position에 지정된 id를 기준으로 상황에 따라 bind호출을 제외.
    }
    override fun getItemId(position: Int): Long = position.toLong()
    enum class ViewType {
        DEFAULT_SIZE_RECYCLER, BIG_SIZE_RECYCLER, AD_RECYCLER, LONG_SIZE_BANNER, MIDDLE_SIZE_RECYCLER, TAG_TO_DEFAULT_RECYCLER, DEFAULT_SIZE_TO_SALE_RECYCLER
    }
    inner class RecyclerViewHolder(private val binding: RecyclerMainItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val viewType = getItemViewType(position)
            val num = when (ViewType.entries[viewType]) {
                ViewType.DEFAULT_SIZE_RECYCLER -> {0}
                ViewType.BIG_SIZE_RECYCLER -> {1}
                ViewType.MIDDLE_SIZE_RECYCLER -> {2}
                ViewType.AD_RECYCLER -> {3}
                ViewType.LONG_SIZE_BANNER -> {4}
                ViewType.TAG_TO_DEFAULT_RECYCLER -> {5}
                ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER -> {6}
            }
            setRecyclerView(binding, num)
        }
    }

    // bindViewHolder는 최대한 가공된 데이터를 set하는 역할만.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "MainRecycler onBindViewHolder: 실행")
        (holder as RecyclerViewHolder).bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "MainRecycler onCreateViewHolder count: ${count.inc()}")
        val view = RecyclerMainItemRecyclerviewBinding.inflate(
            LayoutInflater.from(
                parent.context
            )
        )
        return RecyclerViewHolder(view)
    }

    // entries : 열거형의 모든 값을 배열로 반환
    // ordinal : entries[position]의 순서 반환 -> 특정(position) 배열 값 반환
    // Int 자료형으로 ViewType.entries[position].ordinal값을 반환
    override fun getItemViewType(position: Int): Int = viewTypes[position].ordinal
    override fun getItemCount(): Int = viewTypes.size
    private fun setRecyclerView(
        binding: RecyclerMainItemRecyclerviewBinding,
        choose: Int
    ) {
        // 나중 변경
        if (count >= 7) {
            --count
        }
        binding.apply {
            val bannerItems = root.context.resources.getStringArray(R.array.banner_items)
            ivAd.visibility = View.GONE

            val adapterType = when (choose) {
                // recycler -1=only ad 0=default, 1=big size, 2=middle size, 3=change container, 4=long size banner
                -1 -> {
                    ivAd.visibility = View.VISIBLE
                    incluedLayoutBanner.linearContainer.visibility = View.GONE
                    incluedLayoutBanner.tvTag.text = bannerItems[count++]
                    null
                }

                3 -> {
                    constraintDfContainer.apply {
                        setBackgroundColor(Color.BLUE)
                        incluedLayoutBanner.tvTag.text = bannerItems[count++]
                    }
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }

                else -> {
                    incluedLayoutBanner.tvTag.text = bannerItems[count++]
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }
            }

            adapterType?.let {
                // setLayoutManager.recycleChildrenOnDetach = true // 아이템 뿐 아니라 아이템 내의 View도 모두 재사용 됨.
                rvMainDefaultList.apply {
                    adapter = adapterType
                    layoutManager = LinearLayoutManager(
                        rvMainDefaultList.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )/*.apply{
                    recycleChildrenOnDetach = true
                }*/
                    /*setRecycledViewPool(recyclerViewPool)*/
                    setItemViewCacheSize(viewTypes.size) // 아이템 화면 밖으로 사라져도, n만큼의 항목을 계속 유지 -> 캐싱해두는것. onBind를 실행하지 않아도 됨.
                    setHasFixedSize(true)
                }
            }
        }
    }
}