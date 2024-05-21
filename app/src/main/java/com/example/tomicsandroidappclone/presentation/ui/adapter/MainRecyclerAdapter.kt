package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerItemNoticeBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyDynamicViewAttacher
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper

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

    private var count = 1
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
                ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER -> 4
                ViewType.TAG_TO_DEFAULT_RECYCLER -> 5
                ViewType.LONG_SIZE_BANNER -> 6
                ViewType.NOTICE -> return // 따로 처리
            }
            setRecyclerView(binding, num)
        }
    }

    inner class NoticeViewHolder(private val binding: RecyclerItemNoticeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context) {
            val originalText = context.getString(R.string.notice)
            // 문자열의 마지막 글자 색상 변경
            val spannableText = MyStringMapper.setLastCharacterColor(context, originalText, R.color.tomics_red)
            binding.tvNotice.text = spannableText
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "MainRecycler onBindViewHolder: 실행")
        when (holder) {
            is RecyclerViewHolder -> holder.bind(position)
            is NoticeViewHolder -> holder.bind(holder.itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "MainRecycler onCreateViewHolder: $viewType")
        return when (ViewType.entries[viewType]) {
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
            val bannerItemsDetail = root.context.resources.getStringArray(R.array.banner_items_detail)
            ivAd.visibility = View.GONE
            val label_tags = root.context.resources.getStringArray(R.array.label_tag)


            /**
             *  ViewType.DEFAULT_SIZE_RECYCLER -> 0
             *  ViewType.BIG_SIZE_RECYCLER -> 1
             *  ViewType.MIDDLE_SIZE_RECYCLER -> 2
             *  ViewType.AD_RECYCLER -> 3
             *  ViewType.DEFAULT_SIZE_TO_SALE_RECYCLER -> 4
             *  ViewType.TAG_TO_DEFAULT_RECYCLER -> 5
             *  ViewType.LONG_SIZE_BANNER -> 6
             */
            val adapterType = when (choose) {

                3 -> {
                    incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                    ivAd.visibility = View.VISIBLE
                    if(count == 9){
                        ivAd.setImageResource(R.drawable.ic_main_recycler_ad_2)
                    } else if(count == 11){
                        ivAd.setImageResource(R.drawable.ic_main_recycler_ad_3)
                    }
                    incluedLayoutBanner.clBannerContainer.visibility = View.GONE
                    null
                }
                4 -> {
                    constraintDfContainer.apply {
                        incluedLayoutBanner.clBannerContainer.removeAllViews()
                        setBackgroundResource(R.drawable.recycler_sale_item_background)

                        ivAd.visibility = View.GONE
                        var bannerItemsDetailText = bannerItemsDetail[0]
                        if(count > 10){
                            bannerItemsDetailText = bannerItemsDetail[1]
                        }
                        // dynamicTextView 추가
                        val textView1 = MyDynamicViewAttacher.createTextView(root.context, bannerItems[count++], Color.BLACK, 24f, 0, Typeface.DEFAULT_BOLD)
                        val textView2 =  MyDynamicViewAttacher.createTextView(root.context, bannerItemsDetailText, ContextCompat.getColor(root.context, R.color.tomics_dark_sky_blue), 14f, 0, Typeface.DEFAULT)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView1)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView2)
                        // 기존에 있던 뷰를 새로 추가한 뷰 밑에 다시 추가.
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, rvMainDefaultList)
                    }
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }

                6 -> {
                    incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                    incluedLayoutBanner.tvTag.text = bannerItems[count++]
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }
                else -> {
                    if(count < 6){
                        incluedLayoutBanner.tvLabel.text = label_tags[count]
                    }
                    if(count > 5){
                        incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                        incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvSeeMore)
                    }
                    if(count == 5){
                        val textView = MyDynamicViewAttacher.createTextView(root.context, "#가족", Color.BLACK, 12f, 0, Typeface.DEFAULT, true)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView, position = MyDynamicViewAttacher.Position.LEFT)
                        val textView1 = MyDynamicViewAttacher.createTextView(root.context, "#본능", Color.BLACK, 12f, 0, Typeface.DEFAULT, true)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView1, MyDynamicViewAttacher.Position.LEFT)
                    }
                    incluedLayoutBanner.tvTag.text = bannerItems[count++]
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
