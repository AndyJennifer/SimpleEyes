package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jennifer.andy.base.utils.getElapseTimeForShow
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView


/**
 * Author:  andy.xwt
 * Date:    2018/7/9 15:31
 * Description:
 */

class CollectionOfHorizontalScrollCardView : FrameLayout {

    private val mEliteImageView by bindView<EliteImageView>(R.id.scroll_elite_view)
    private val mTvTitle by bindView<CustomFontTextView>(R.id.scroll_tv_title)
    private val mTvDesc by bindView<CustomFontTextView>(R.id.scroll_tv_desc)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_collection_of_horizontal_scroll_card, this, true)
    }


    fun setData(content: Content) {
        setImageUrl(content.data.cover.feed)
        val description = "#${content.data.category}   /   ${getElapseTimeForShow(content.data.duration)}"
        setTitleAndDesc(content.data.title, description)
        setDailyVisible(content.data.library == "DAILY")
    }

    /**
     * 设置图片显示
     * @param url 图片地址
     */
    private fun setImageUrl(url: String) {
        mEliteImageView.setImageUrl(url)
    }

    /**
     * 是指标题与描述
     */
    private fun setTitleAndDesc(title: String, desc: String) {
        mTvTitle.text = title
        mTvDesc.text = desc
    }

    /**
     * 设置精选是否可见
     */
    private fun setDailyVisible(visible: Boolean) {
        mEliteImageView.setDailyVisible(visible)
    }
}