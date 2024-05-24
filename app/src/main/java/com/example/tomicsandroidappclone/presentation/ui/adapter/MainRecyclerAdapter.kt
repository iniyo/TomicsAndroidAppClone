package com.example.tomicsandroidappclone.presentation.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.RecyclerItemNoticeBinding
import com.example.tomicsandroidappclone.databinding.RecyclerMainItemRecyclerviewBinding
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.easyutil.MyDynamicViewAttacher
import com.example.tomicsandroidappclone.presentation.util.mapper.MyStringMapper
import com.google.android.flexbox.FlexboxLayout

class MainRecyclerAdapter(
    private val webtoonData: ArrayList<Webtoon>
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

    inner class NoticeViewHolder(private val binding: RecyclerItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context) {
            val originalText = context.getString(R.string.notice)
            // 문자열의 마지막 글자 색상 변경
            val spannableText =
                MyStringMapper.setLastCharacterColor(context, originalText, R.color.tomics_red)
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
                val binding = RecyclerItemNoticeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                NoticeViewHolder(binding)
            }

            else -> {
                val binding = RecyclerMainItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
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
            val bannerItemsDetail =
                root.context.resources.getStringArray(R.array.banner_items_detail)
            ivAd.visibility = View.GONE
            val labelTags = root.context.resources.getStringArray(R.array.label_tags) // tag앞 label
            val bespokeTags = root.context.resources.getStringArray(R.array.bespoke_tags) // 맞춤 태그
            val genreTage =
                root.context.resources.getStringArray(R.array.free_webtoon_tab_items) // 장르 태그
            val paddingSet = 25
            val tagLayout = binding.root.findViewById<ConstraintLayout>(R.id.tag_layout)
            val layoutBanner =
                binding.root.findViewById<ConstraintLayout>(R.id.inclued_layout_banner)
            val flexboxLayout = tagLayout.findViewById<FlexboxLayout>(R.id.flexbox_layout)

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

                // only ad
                3 -> {
                    incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                    ivAd.visibility = View.VISIBLE
                    if (count == 9) {
                        ivAd.setImageResource(R.drawable.ic_main_recycler_ad_2)
                    } else if (count == 11) {
                        ivAd.setImageResource(R.drawable.ic_main_recycler_ad_3)
                    }
                    incluedLayoutBanner.clBannerContainer.visibility = View.GONE
                    null
                }
                // sale toon list
                4 -> {
                    constraintDfContainer.apply {
                        constraintDfContainer.removeView(incluedLayoutBanner.clBannerContainer)
                        setBackgroundResource(R.drawable.recycler_sale_item_background)

                        ivAd.visibility = View.GONE
                        var bannerItemsDetailText = bannerItemsDetail[0]
                        if (count > 10) {
                            bannerItemsDetailText = bannerItemsDetail[1]
                        }
                        // dynamicTextView 추가
                        val textView1 = MyDynamicViewAttacher.createTextView(
                            root.context,
                            bannerItems[count++],
                            Color.BLACK,
                            24f,
                            0,
                            Typeface.DEFAULT_BOLD
                        )
                        val textView2 = MyDynamicViewAttacher.createTextView(
                            root.context,
                            bannerItemsDetailText,
                            ContextCompat.getColor(root.context, R.color.tomics_dark_sky_blue),
                            14f,
                            0,
                            Typeface.DEFAULT
                        )
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView1)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, textView2)
                        // 기존에 있던 뷰를 새로 추가한 뷰 밑에 다시 추가.
                        MyDynamicViewAttacher.addViewPosition(
                            constraintDfContainer,
                            rvMainDefaultList
                        )
                        rvMainDefaultList.setPadding(paddingSet, 0, 0, 0)
                    }
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }
                // long size ad banner
                6 -> {
                    incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                    incluedLayoutBanner.tvTag.text = bannerItems[count++]
                    RecyclerDefaultToonAdapter(webtoonData, choose)
                }

                else -> {
                    // just set lebelTags
                    if (count < 6) {
                        incluedLayoutBanner.tvLabel.text = labelTags[count]
                    }
                    // just remove
                    if (count > 5) {
                        incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvLabel)
                        incluedLayoutBanner.clBannerContainer.removeView(incluedLayoutBanner.tvSeeMore)
                    }
                    // RadioButton tag attach
                    if (count == 4) {
                        var selectedRadioButton: RadioButton? = null

                        // 기본 색상과 선택된 색상 정의
                        val selectedTextColor = ContextCompat.getColor(root.context, R.color.white)
                        val defaultTextColor = ContextCompat.getColor(root.context, R.color.black)

                        bespokeTags.forEachIndexed { index, tag ->
                            val radioButton = RadioButton(root.context).apply {
                                text = tag
                                setBackgroundResource(R.drawable.selector_custom_radio_for_main_recycler_tag)
                                buttonDrawable = null // 기존 버튼 이미지를 없애기
                                setTextColor(defaultTextColor) // 기본 텍스트 색상 설정
                                val paddingHorizontal = 28
                                val paddingVertical = 18
                                setPadding(
                                    paddingHorizontal,
                                    paddingVertical,
                                    paddingHorizontal,
                                    paddingVertical
                                )
                                layoutParams = FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    setMargins(4, 4, 4, 4)
                                }

                                setOnClickListener {
                                    selectedRadioButton?.apply {
                                        isChecked = false
                                        setTextColor(defaultTextColor) // 이전 선택된 버튼의 색상 기본으로 변경
                                    }
                                    selectedRadioButton = this.apply {
                                        isChecked = true
                                        setTextColor(selectedTextColor) // 선택된 버튼의 색상 변경
                                    }
                                }

                                // 첫 번째 라디오 버튼을 선택된 상태로 설정
                                if (index == 0) {
                                    isChecked = true
                                    setTextColor(selectedTextColor)
                                    selectedRadioButton = this
                                }
                            }
                            flexboxLayout.addView(radioButton)
                        }

                        // view attach
                        MyDynamicViewAttacher.addViewPosition(
                            constraintDfContainer,
                            layoutBanner
                        )
                        layoutBanner.setPadding(paddingSet, 0, paddingSet, 0)
                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, tagLayout)
                        MyDynamicViewAttacher.addViewPosition(
                            constraintDfContainer,
                            rvMainDefaultList
                        )
                        rvMainDefaultList.setPadding(paddingSet, 0, paddingSet, 0)
                    }

                    // TextView tag attach
                    if (count == 5) {

                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, layoutBanner)
                        layoutBanner.setPadding(paddingSet, 0, paddingSet, 0)
                        flexboxLayout.removeAllViews()

                        var selectedRadioButton: RadioButton? = null

                        // 기본 색상과 선택된 색상 정의
                        val selectedTextColor = ContextCompat.getColor(root.context, R.color.black)
                        val defaultTextColor =
                            ContextCompat.getColor(root.context, R.color.dark_gray)

                        val genreTags = genreTage.slice(1..5) // genreTage 리스트에서 1부터 5까지 가져오기

                        genreTags.forEachIndexed { index, tag ->
                            val radioButton = RadioButton(root.context).apply {
                                text = tag
                                textSize = 16f
                                buttonDrawable = null // 기존 버튼 이미지를 없애기
                                setTextColor(defaultTextColor) // 기본 텍스트 색상 설정
                                layoutParams = FlexboxLayout.LayoutParams(
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                                )
                                setPadding(paddingSet / 3, 0, paddingSet / 3, 0)

                                setOnClickListener {
                                    selectedRadioButton?.apply {
                                        isChecked = false
                                        typeface = Typeface.DEFAULT
                                        setTextColor(defaultTextColor) // 이전 선택된 버튼의 색상 기본으로 변경
                                    }
                                    selectedRadioButton = this.apply {
                                        isChecked = true
                                        typeface = Typeface.DEFAULT_BOLD
                                        setTextColor(selectedTextColor) // 선택된 버튼의 색상 변경
                                    }
                                }

                                // 첫 번째 라디오 버튼을 선택된 상태로 설정
                                if (index == 0) {
                                    isChecked = true
                                    typeface = Typeface.DEFAULT_BOLD
                                    setTextColor(selectedTextColor)
                                    selectedRadioButton = this
                                }
                            }
                            flexboxLayout.addView(radioButton)

                            // 마지막 아이템이 아닌 경우 "|" 추가
                            if (index < genreTags.size - 1) {
                                val separator = RadioButton(root.context).apply {
                                    text = "|"
                                    textSize = 16f
                                    buttonDrawable = null
                                    setTextColor(defaultTextColor) // 기본 텍스트 색상 설정
                                    layoutParams = FlexboxLayout.LayoutParams(
                                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                                    )
                                    setPadding(paddingSet / 3, 0, paddingSet / 3, 0)
                                    isClickable = false // 클릭x
                                }
                                flexboxLayout.addView(separator)
                            }
                        }

                        MyDynamicViewAttacher.addViewPosition(constraintDfContainer, tagLayout)
                        MyDynamicViewAttacher.addViewPosition(
                            constraintDfContainer,
                            rvMainDefaultList
                        )
                        rvMainDefaultList.setPadding(paddingSet, 0, paddingSet, 0)
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
