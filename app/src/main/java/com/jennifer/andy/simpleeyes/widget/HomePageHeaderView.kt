package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.ItemList
import com.jennifer.andy.simpleeyes.entity.TopIssue
import com.jennifer.andy.simpleeyes.image.FrescoImageLoader
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTypeWriterTextView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


/**
 * Author:  andy.xwt
 * Date:    2017/11/27 13:55
 * Description:主界面头部布局
 */

class HomePageHeaderView : FrameLayout {

    private val mBanner: Banner by bindView(R.id.head_banner)
    private val mTitle: CustomFontTypeWriterTextView by bindView(R.id.tv_title)
    private val mText: CustomFontTypeWriterTextView by bindView(R.id.tv_text)
    private val mHeadRefreshView: HeaderRefreshView by bindView(R.id.head_refresh)
    private val mIvSearch: ImageView by bindView(R.id.iv_search)

    private lateinit var mTopIssue: TopIssue
    private lateinit var mBaseFragment: BaseFragment<*, *>

    private var mScrollValue = 0
    private var currentPosition = -1

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_category_head_view, this, true)
        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        //设置banner滑动监听
        mBanner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                //播放动画，并设置打印文字
                if (currentPosition != position) {//处理banner的position问题
                    mTitle.printText(mTopIssue.data.itemList[position].data.title)
                    mText.printText(mTopIssue.data.itemList[position].data.slogan)
                    currentPosition = position
                }
            }
        })
        mIvSearch.setOnClickListener {
            mBaseFragment.readyGo(SearchHotActivity::class.java, null)
        }

    }

    /**
     * 设置头部信息
     */
    fun setHeaderInfo(topIssue: TopIssue, videoListInfo: MutableList<ItemList>, baseFragment: BaseFragment<*, *>) {
        mTopIssue = topIssue
        mBaseFragment = baseFragment
        mBanner.setImageLoader(FrescoImageLoader())
        mBanner.setImages(getTopIssueCardUrl(topIssue.data.itemList))
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        mBanner.setIndicatorGravity(BannerConfig.CENTER)
        mBanner.isAutoPlay(true)
        mBanner.start()
        mBanner.setDelayTime(6000)
        mBanner.setOnBannerListener {
            val item = mTopIssue.data.itemList[it]
            val bundle = Bundle()
            bundle.putSerializable(Extras.VIDEO_LIST_INFO, videoListInfo as ArrayList)
            bundle.putSerializable(Extras.VIDEO_INFO, item)
            val intent = Intent(context, VideoDetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    /**
     * 显示刷新遮罩
     */
    fun showRefreshCover(scrollValue: Int) {
        mScrollValue = scrollValue
        mHeadRefreshView.showRefreshCover(scrollValue)
        mBanner.stopAutoPlay()
    }

    /**
     * 关闭刷新遮罩
     */
    fun hideRefreshCover() {
        mHeadRefreshView.hideRefreshCover()
        mBanner.startAutoPlay()
    }

    /**
     * 获取顶部图片地址集合
     */
    private fun getTopIssueCardUrl(itemList: MutableList<Content>) = itemList.map { it.data.cover.feed }

    /**
     * 判断是否达到刷新阀值,
     */
    fun judgeCanRefresh() = mScrollValue >= mHeadRefreshView.getRefreshThresholdValue()


}