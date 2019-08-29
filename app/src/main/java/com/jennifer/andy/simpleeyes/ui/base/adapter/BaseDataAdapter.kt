package com.jennifer.andy.simpleeyes.ui.base.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.ui.home.adapter.CollectionCardCoverAdapter
import com.jennifer.andy.simpleeyes.ui.home.adapter.SquareCollectionAdapter
import com.jennifer.andy.simpleeyes.ui.search.adapter.CollectionBriefAdapter
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.utils.DensityUtils
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.jennifer.andy.simpleeyes.widget.EliteImageView
import com.jennifer.andy.simpleeyes.widget.ItemHeaderView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.image.imageloader.FrescoImageLoader
import com.jennifer.andy.simpleeyes.widget.viewpager.MarginWithIndicatorViewPager
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/10/27 16:35
 * Description: 基础信息适配器
 */

open class BaseDataAdapter(data: MutableList<Content>) : BaseQuickAdapter<Content, BaseViewHolder>(data) {


    /**
     * 卡片类型
     */
    companion object {
        const val BANNER_TYPE = 0
        const val FOLLOW_CARD_TYPE = 1
        const val HORIZONTAL_SCROLL_CARD_TYPE = 2
        const val VIDEO_COLLECTION_WITH_COVER_TYPE = 3
        const val SQUARE_CARD_COLLECTION_TYPE = 4
        const val VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE = 5
        const val VIDEO_COLLECTION_WITH_BRIEF_TYPE = 6
        const val TEXT_CARD_TYPE = 7
        const val BRIEF_CARD_TYPE = 8
        const val BLANK_CARD_TYPE = 9
        const val SQUARE_CARD_TYPE = 10
        const val RECTANGLE_CARD_TYPE = 11
        const val VIDEO_TYPE = 12
        const val VIDEO_BANNER_THREE_TYPE = 13
        const val VIDEO_SMALL_CARD_TYPE = 14

        const val BANNER = "banner"
        const val FOLLOW_CARD = "followCard"
        const val HORIZONTAL_CARD = "horizontalScrollCard"
        const val VIDEO_COLLECTION_WITH_COVER = "videoCollectionWithCover"
        const val SQUARE_CARD_COLLECTION = "squareCardCollection"
        const val VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD = "videoCollectionOfHorizontalScrollCard"
        const val VIDEO_COLLECTION_WITH_BRIEF = "videoCollectionWithBrief"
        const val TEXT_CARD = "textCard"
        const val BRIEF_CARD = "briefCard"
        const val BLANK_CARD = "blankCard"
        const val SQUARE_CARD = "squareCard"
        const val RECTANGLE_CARD = "rectangleCard"
        const val VIDEO = "video"
        const val VIDEO_BANNER_THREE = "banner3"
        const val VIDEO_SMALL_CARD = "videoSmallCard"
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<Content>() {
            override fun getItemType(andyInfoItem: Content?): Int {
                when (andyInfoItem?.type) {
                    BANNER -> return BANNER_TYPE
                    FOLLOW_CARD -> return FOLLOW_CARD_TYPE
                    HORIZONTAL_CARD -> return HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_COVER -> return VIDEO_COLLECTION_WITH_COVER_TYPE
                    SQUARE_CARD_COLLECTION -> return SQUARE_CARD_COLLECTION_TYPE
                    VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD -> return VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_BRIEF -> return VIDEO_COLLECTION_WITH_BRIEF_TYPE
                    TEXT_CARD -> return TEXT_CARD_TYPE
                    BRIEF_CARD -> return BRIEF_CARD_TYPE
                    BLANK_CARD -> return BLANK_CARD_TYPE
                    SQUARE_CARD -> return SQUARE_CARD_TYPE
                    RECTANGLE_CARD -> return RECTANGLE_CARD_TYPE
                    VIDEO -> return VIDEO_TYPE
                    VIDEO_BANNER_THREE -> return VIDEO_BANNER_THREE_TYPE
                    VIDEO_SMALL_CARD -> return VIDEO_SMALL_CARD_TYPE

                }
                return FOLLOW_CARD_TYPE
            }
        }
        with(multiTypeDelegate) {
            registerItemType(BANNER_TYPE, R.layout.layout_card_banner)
            registerItemType(FOLLOW_CARD_TYPE, R.layout.layout_follow_card)
            registerItemType(HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_horizontal_scroll_card)
            registerItemType(VIDEO_COLLECTION_WITH_COVER_TYPE, R.layout.layout_collection_with_cover)
            registerItemType(SQUARE_CARD_COLLECTION_TYPE, R.layout.layout_square_collection)
            registerItemType(VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE, R.layout.item_collection_of_horizontal_scroll_card)
            registerItemType(VIDEO_COLLECTION_WITH_BRIEF_TYPE, R.layout.layout_collection_with_brief)
            registerItemType(TEXT_CARD_TYPE, R.layout.layout_single_text)
            registerItemType(BRIEF_CARD_TYPE, R.layout.layout_brife_card)
            registerItemType(BLANK_CARD_TYPE, R.layout.layout_blank_card)
            registerItemType(SQUARE_CARD_TYPE, R.layout.item_square_card)
            registerItemType(RECTANGLE_CARD_TYPE, R.layout.item_square_card)
            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
            registerItemType(VIDEO_BANNER_THREE_TYPE, R.layout.layout_follow_card)
            registerItemType(VIDEO_SMALL_CARD_TYPE, R.layout.layout_video_small_card)
        }
        //设置倒数第5个开启预加载
        setPreLoadNumber(5)
    }

    override fun convert(helper: BaseViewHolder?, item: Content) {
        when (helper?.itemViewType) {
            BANNER_TYPE -> setBannerInfo(helper, item)
            FOLLOW_CARD_TYPE -> setFollowCardInfo(helper, item)
            HORIZONTAL_SCROLL_CARD_TYPE -> setHorizontalScrollCardInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_WITH_COVER_TYPE -> setCollectionCardWithCoverInfo(helper, item.data)
            SQUARE_CARD_COLLECTION_TYPE -> setSquareCollectionInfo(helper, item.data)
            VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE -> setCollectionOfHorizontalScrollCardInfo(helper, item)
            VIDEO_COLLECTION_WITH_BRIEF_TYPE -> setCollectionBriefInfo(helper, item)
            TEXT_CARD_TYPE -> setSingleText(helper, item)
            BRIEF_CARD_TYPE -> setBriefCardInfo(helper, item)
            BLANK_CARD_TYPE -> setBlankCardInfo(helper, item)
            SQUARE_CARD_TYPE -> setSquareCardInfo(helper, item.data)
            RECTANGLE_CARD_TYPE -> setRectangleCardInfo(helper, item.data)
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            VIDEO_BANNER_THREE_TYPE -> setBanner3Info(helper, item.data)
            VIDEO_SMALL_CARD_TYPE -> setSmallCardInfo(helper, item.data)
        }

    }


    /**
     * 设置视频banner信息
     */
    private fun setBannerInfo(helper: BaseViewHolder, item: Content) {
        with(helper) {
            getView<SimpleDraweeView>(R.id.iv_image).setImageURI(item.data.image)
            itemView.setOnClickListener {
                if (!TextUtils.isEmpty(item.data.actionUrl)) {
                    mContext.startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(item.data.actionUrl)
                        addCategory(Intent.CATEGORY_DEFAULT)
                        addCategory(Intent.CATEGORY_BROWSABLE)
                    })
                }
            }
        }
    }


    /**
     * 设置单视频卡片信息
     */
    private fun setFollowCardInfo(helper: BaseViewHolder, item: Content) {

        with(helper) {
            val info = item.data
            //设置头布局
            info.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, info.type) }

            getView<EliteImageView>(R.id.elite_view).apply {
                setImageUrl(info.content!!.data.cover.feed)
                setDailyVisible(info.content!!.data.library == "DAILY")
                setOnClickListener {
                    //跳转到视频详细界面
                    VideoDetailActivity.start(mContext!!, item.data.content?.data!!, mData as ArrayList, mData.indexOf(item))
                }
            }
        }

    }

    /**
     * 设置水平滚动卡片集合信息
     */
    private fun setCollectionOfHorizontalScrollCardInfo(helper: BaseViewHolder, content: Content) {

        with(helper) {
            //设置头布局
            val data = content.data
            data.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, content.type) }
            //跳转到视频详细界面
            getView<MarginWithIndicatorViewPager>(R.id.view_pager).apply {
                pageViewClickListener = {
                    val content = data.itemList[it].data
                    VideoDetailActivity.start(mContext!!, content, data.itemList as ArrayList, it)

                }
                setData(data.itemList)
            }
        }
    }


    /**
     * 设置带关注人的视频集合信息
     */
    private fun setCollectionBriefInfo(helper: BaseViewHolder, content: Content) {

        with(helper) {

            getView<RecyclerView>(R.id.rv_recycler).apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = CollectionBriefAdapter(content.data.itemList).apply {
                    onItemClickListener = OnItemClickListener { _, _, position ->
                        //跳转到播放视频详情
                        val item = getItem(position)
                        VideoDetailActivity.start(mContext, item!!.data, data as ArrayList<Content>, position)

                    }
                }
            }

            //设置头布局
            content.data.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, content.data.type) }

        }
    }


    /**
     * 设置文字信息
     */
    private fun setSingleText(helper: BaseViewHolder, item: Content) {
        helper.getView<CustomFontTextView>(R.id.tv_text).apply {
            text = item.data.text
            when (item.data.type) {
                "header1" -> {
                    setFontType(item.data.header?.font, FontType.LOBSTER)
                    gravity = Gravity.CENTER
                }
                "header2" -> {
                    setFontType(item.data.header?.font, FontType.BOLD)
                    gravity = Gravity.CENTER
                }
                else -> {
                    gravity = Gravity.START
                    setFontType(item.data.header?.font, FontType.NORMAL)
                }
            }
        }
    }

    /**
     * 设置合作作者信息
     */
    private fun setBriefCardInfo(helper: BaseViewHolder, content: Content) {
        helper.getView<ItemHeaderView>(R.id.item_header_view).setAuthorHeader(content.data)

    }

    /**
     * 设置空白信息高度
     */
    private fun setBlankCardInfo(helper: BaseViewHolder, content: Content) {
        helper.getView<View>(R.id.view).apply {
            val lp = layoutParams
            lp.height = DensityUtils.dip2px(mContext, content.data.height.toFloat())
            layoutParams = lp
        }
    }


    /**
     * 设置banner3（广告信息）信息
     */
    private fun setBanner3Info(helper: BaseViewHolder, item: ContentBean) {
        with(helper) {
            //设置头布局
            item.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, item.type) }

            getView<EliteImageView>(R.id.elite_view).apply {
                setImageUrl(item.image)
                setDailyVisible(false)
                setTranslateText(mContext.getString(R.string.advert))
            }

        }
    }


    /**
     * 设置水平滚动卡片信息
     */
    private fun setHorizontalScrollCardInfo(helper: BaseViewHolder, itemList: MutableList<Content>) {
        helper.getView<Banner>(R.id.banner).apply {
            setImageLoader(FrescoImageLoader())
            setImages(getHorizonTalCardUrl(itemList))
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            isAutoPlay(true)
            start()
            setDelayTime(5000)
            setOnBannerListener {
                //跳转到过滤界面
                mContext.startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(itemList[it].data.actionUrl)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    addCategory(Intent.CATEGORY_BROWSABLE)
                })
            }
        }
    }

    /**
     * 获取水平卡片图片地址
     */
    private fun getHorizonTalCardUrl(itemList: MutableList<Content>) = itemList.map { it.data.image }


    /**
     * 设置集合卡片信息
     */
    private fun setCollectionCardWithCoverInfo(helper: BaseViewHolder, item: ContentBean) {
        with(helper) {

            getView<EliteImageView>(R.id.iv_image).apply {
                item.header?.cover?.let { setImageUrl(it) }
                setArrowVisible(true)
            }

            getView<RecyclerView>(R.id.rv_collection_cover_recycler).apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = CollectionCardCoverAdapter(item.itemList)

            }

        }

    }

    /**
     * 设置矩形卡片信息
     */
    private fun setSquareCollectionInfo(helper: BaseViewHolder, item: ContentBean) {
        val itemList: MutableList<Content> = item.itemList
        with(helper) {

            getView<RecyclerView>(R.id.rv_square_recycler).apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = SquareCollectionAdapter(itemList).apply {
                    onItemClickListener = OnItemClickListener { _, _, position ->
                        ARouter.getInstance()
                                .build(Uri.parse(itemList[position].data.actionUrl))
                                .navigation()
                    }
                }

            }

            item.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, item.type) }
        }
    }

    /**
     * 设置长方形卡片信息
     */
    private fun setSquareCardInfo(helper: BaseViewHolder, item: ContentBean) {
        helper.apply {
            getView<SimpleDraweeView>(R.id.iv_simple_image).setImageURI(item.image)
            setText(R.id.tv_title, item.title)
        }
    }

    /**
     * 设置正方形卡片信息
     */
    private fun setRectangleCardInfo(helper: BaseViewHolder, item: ContentBean) {
        helper.apply {
            getView<SimpleDraweeView>(R.id.iv_simple_image).setImageURI(item.image)
            setText(R.id.tv_title, item.title)
        }

    }

    /**
     * 设置单视频信息
     */
    private fun setSingleVideoInfo(helper: BaseViewHolder, item: Content) {
        with(helper) {
            getView<SimpleDraweeView>(R.id.iv_image).setImageURI(item.data.cover.feed)
            setText(R.id.tv_single_title, item.data.title)
            val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
            setText(R.id.tv_single_desc, description)


            //设置label
            if (item.data.label != null) {
                setGone(R.id.tv_label, true)
                setText(R.id.tv_label, item.data.label?.text)
            } else {
                setGone(R.id.tv_label, false)
            }
            //点击跳转到视频界面
            itemView.setOnClickListener { VideoDetailActivity.start(mContext, item.data, data as ArrayList<Content>) }

        }
    }

    /**
     * 设置小视频卡片信息
     */
    private fun setSmallCardInfo(helper: BaseViewHolder, data: ContentBean) {
        with(helper) {
            getView<SimpleDraweeView>(R.id.iv_image).setImageURI(data.cover.feed)
            setText(R.id.tv_title, data.title)
            val description = "#${data.category}   /   ${TimeUtils.getElapseTimeForShow(data.duration)}"
            setText(R.id.tv_desc, description)
            itemView.setOnClickListener { VideoDetailActivity.start(mContext, data, arrayListOf()) }
        }
    }

}