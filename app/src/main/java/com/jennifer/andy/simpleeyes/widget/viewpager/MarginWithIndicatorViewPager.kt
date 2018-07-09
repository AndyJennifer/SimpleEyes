package com.jennifer.andy.simpleeyes.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/7/9 17:11
 * Description: 带分割（可以看见前面视图的）与指示器的ViewPager
 */

class MarginWithIndicatorViewPager : FrameLayout {

    private val mViewPager: ViewPager by bindView(R.id.view_pager)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_margin_with_indicator_pager, this, true)
        // todo 设置无限滚动，设置数据
    }
}