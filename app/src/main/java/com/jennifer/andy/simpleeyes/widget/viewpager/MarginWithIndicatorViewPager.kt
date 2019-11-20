package com.jennifer.andy.simpleeyes.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.utils.dip2px
import com.jennifer.andy.simpleeyes.widget.CollectionOfHorizontalScrollCardView
import com.rd.PageIndicatorView


/**
 * Author:  andy.xwt
 * Date:    2018/7/9 17:11
 * Description: 带分割（可以看见前面视图的）与指示器的ViewPager
 */

class MarginWithIndicatorViewPager : FrameLayout {

    private val mViewPager: ViewPager by bindView(R.id.vp_indicator_pager)
    private val mIndicator: PageIndicatorView by bindView(R.id.pageIndicatorView)

    private lateinit var mItemList: MutableList<Content>
    lateinit var pageViewClickListener: (position: Int) -> Unit

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_margin_with_indicator_pager, this, true)
    }

    /**
     * 设置数据
     */
    fun setData(itemList: MutableList<Content>) {
        mItemList = itemList
        setAdapter()
    }

    private fun setAdapter() {
        mViewPager.adapter = MarginWithViewPagerAdapter()
        mViewPager.offscreenPageLimit = 3
        mViewPager.pageMargin = context.dip2px(10f)
        mViewPager.currentItem = 10000 * mItemList.size
        mIndicator.count = mItemList.size
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mIndicator.setSelected(position % mItemList.size)
            }

            override fun onPageSelected(position: Int) {
            }
        })

    }

    /**
     * 设置无限滚动适配器
     */
    inner class MarginWithViewPagerAdapter : PagerAdapter() {

        override fun getCount() = Int.MAX_VALUE

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val cardView = CollectionOfHorizontalScrollCardView(this@MarginWithIndicatorViewPager.context)
            val newPosition = position % mItemList.size
            cardView.setOnClickListener { pageViewClickListener(newPosition) }//点击响应事件
            cardView.setData(mItemList[newPosition])
            container.addView(cardView)
            return cardView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

    }
}