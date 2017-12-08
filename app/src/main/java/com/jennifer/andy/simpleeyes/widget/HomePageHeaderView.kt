package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ItemListBean
import com.jennifer.andy.simpleeyes.entity.TopIssueBean
import com.jennifer.andy.simpleeyes.image.FrescoImageLoader
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.PrintSpanGroup
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


/**
 * Author:  andy.xwt
 * Date:    2017/11/27 13:55
 * Description:
 */

class HomePageHeaderView : FrameLayout {


    private lateinit var mBanner: Banner
    private lateinit var mTitle: CustomFontTextView
    private lateinit var mText: CustomFontTextView
    private lateinit var mTopIssueBean: TopIssueBean
    private lateinit var mSloganSpanGroup: PrintSpanGroup
    private lateinit var mTitleSpanGroup: PrintSpanGroup

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    /**
     * 初始化
     */
    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_category_head_view, this, true)
        mBanner = view.findViewById(R.id.banner)
        mTitle = view.findViewById(R.id.tv_title)
        mText = view.findViewById(R.id.tv_text)

        //设置banner滑动监听
        mBanner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                //播放动画，并设置打印文字
                mSloganSpanGroup = PrintSpanGroup(mTopIssueBean.data.itemList[position].data.slogan)
                mTitleSpanGroup = PrintSpanGroup(mTopIssueBean.data.itemList[position].data.title)
                mSloganSpanGroup.startPrint(mText)
                mTitleSpanGroup.startPrint(mTitle)
            }
        })
        //下拉刷新的时候停止滑动，并显示加载动画
    }

    /**
     * 设置头部信息
     */
    fun setHeaderInfo(topIssueBean: TopIssueBean) {
        mTopIssueBean = topIssueBean
        mBanner.setImageLoader(FrescoImageLoader())
        mBanner.setImages(getTopIssueCardUrl(topIssueBean.data.itemList))
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        mBanner.setIndicatorGravity(BannerConfig.CENTER)
        mBanner.isAutoPlay(true)
        mBanner.start()
        mBanner.setDelayTime(6000)
        mBanner.setOnBannerListener {
            //todo 点击跳转
        }
    }

    /**
     * 获取顶部图片地址集合
     */
    private fun getTopIssueCardUrl(itemList: MutableList<ItemListBean>) = itemList.map { it.data.cover.feed }


}