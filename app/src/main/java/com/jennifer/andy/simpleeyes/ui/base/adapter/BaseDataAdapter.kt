package com.jennifer.andy.simpleeyes.ui.base.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.ui.category.adapter.CollectionCardCoverAdapter
import com.jennifer.andy.simpleeyes.ui.category.adapter.SquareCollectionAdapter
import com.jennifer.andy.simpleeyes.ui.search.adapter.CollectionBriefAdapter
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.utils.DensityUtils
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.jennifer.andy.simpleeyes.widget.CardNormalBottom
import com.jennifer.andy.simpleeyes.widget.EliteImageView
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
        }

    }

    override fun convert(helper: BaseViewHolder?, item: Content) {
        when (helper?.itemViewType) {
            BANNER_TYPE -> setBannerInfo(helper, item)
            FOLLOW_CARD_TYPE -> setFollowCardInfo(helper, item)
            HORIZONTAL_SCROLL_CARD_TYPE -> setHorizontalScrollCardInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_WITH_COVER_TYPE -> setCollectionCardWithCoverInfo(helper, item.data)
            SQUARE_CARD_COLLECTION_TYPE -> setSquareCollectionInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_OF_HORIZONTAL_SCROLL_CARD_TYPE -> setCollectionOfHorizontalScrollCardInfo(helper, item.data)
            VIDEO_COLLECTION_WITH_BRIEF_TYPE -> setCollectionBriefInfo(helper, item)
            TEXT_CARD_TYPE -> setSingleText(helper, item)
            BRIEF_CARD_TYPE -> setBriefCardInfo(helper, item)
            BLANK_CARD_TYPE -> setBlankCardInfo(helper, item)
            SQUARE_CARD_TYPE -> setSquareCardInfo(helper, item.data)
            RECTANGLE_CARD_TYPE -> setRectangleCardInfo(helper, item.data)
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            VIDEO_BANNER_THREE_TYPE -> setBanner3Info(helper, item.data)
        }

    }


    /**
     * 设置视频banner信息
     */
    private fun setBannerInfo(helper: BaseViewHolder, item: Content) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.image)
        helper.itemView.setOnClickListener {
            if (!TextUtils.isEmpty(item.data.actionUrl)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.data.actionUrl)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                mContext.startActivity(intent)
            }
        }
    }


    /**
     * 设置单视频卡片信息
     */
    private fun setFollowCardInfo(helper: BaseViewHolder, item: Content) {
        val info = item.data
        val cardNormalBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        with(cardNormalBottom) {
            setTitle(info.header.title)
            setDescription(info.header.description)
            setIconUrl(info.header.icon)
            setIconType(info.header.iconType == "round")//设置图标形状
        }

        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        with(eliteView) {
            setImageUrl(info.content!!.data.cover.feed)
            setDailyVisible(info.content!!.data.library == "DAILY")
        }

        //跳转到视频详细界面
        helper.itemView.setOnClickListener {
            VideoDetailActivity.start(mContext!!, item.data.content?.data!!, mData as ArrayList, mData.indexOf(item))
        }
    }

    /**
     * 设置水平滚动卡片集合信息
     */
    private fun setCollectionOfHorizontalScrollCardInfo(helper: BaseViewHolder, data: ContentBean) {
        helper.setText(R.id.tv_title, data.header.title)
        helper.setText(R.id.tv_sub_title, data.header.subTitle)
        val marginWithIndicatorViewPager = helper.getView<MarginWithIndicatorViewPager>(R.id.view_pager)

        //跳转到视频详细界面
        marginWithIndicatorViewPager.pageViewClickListener = {
            val content = data.itemList[it].data
            VideoDetailActivity.start(mContext!!, content, data.itemList as ArrayList, it)
        }
        marginWithIndicatorViewPager.setData(data.itemList)
    }


    /**
     * 设置带关注人的视频集合信息
     */
    private fun setCollectionBriefInfo(helper: BaseViewHolder, content: Content) {
        //设置视频集合信息
        val recyclerView = helper.getView<RecyclerView>(R.id.rv_recycler)
        recyclerView.isNestedScrollingEnabled = false
        val collectionBriefAdapter = CollectionBriefAdapter(content.data.itemList)
        recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = collectionBriefAdapter
        collectionBriefAdapter.onItemClickListener = OnItemClickListener { _, _, position ->
            //跳转到播放视频详情
            val item = collectionBriefAdapter.getItem(position)
            VideoDetailActivity.start(mContext, item!!.data, collectionBriefAdapter.data as ArrayList<Content>, position)

        }
        //设置作者信息
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_head)
        if (content.data.header.iconType == "round") {//判断头像类型
            imageView.hierarchy.roundingParams = RoundingParams.asCircle()
        } else {
            imageView.hierarchy.roundingParams?.roundAsCircle = false
        }
        imageView.setImageURI(content.data.header.icon)
        helper.setText(R.id.tv_title, content.data.header.title)
        helper.setText(R.id.tv_desc, content.data.header.description)

    }


    /**
     * 设置文字信息
     */
    private fun setSingleText(helper: BaseViewHolder, item: Content) {
        helper.setText(R.id.tv_text, item.data.text)
    }

    /**
     * 设置合作作者信息
     */
    private fun setBriefCardInfo(helper: BaseViewHolder, item: Content) {
        val mIcon = helper.getView<SimpleDraweeView>(R.id.iv_source)
        val isCircle = item.data.iconType == "round"
        if (isCircle) mIcon.hierarchy.roundingParams = RoundingParams.asCircle()
        else mIcon.hierarchy.roundingParams?.roundAsCircle = false
        //设置头像
        mIcon.setImageURI(item.data.icon)
        //设置标题
        helper.setText(R.id.tv_title, item.data.title)
        //设置描述
        helper.setText(R.id.tv_desc, item.data.description)
        //todo 点击跳转到作者详情
    }

    /**
     * 设置空白信息高度
     */
    private fun setBlankCardInfo(helper: BaseViewHolder, item: Content) {
        val view = helper.getView<View>(R.id.view)
        val layoutParams = view.layoutParams
        layoutParams.height = DensityUtils.dip2px(mContext, item.data.height.toFloat())
        view.layoutParams = layoutParams
    }


    /**
     * 设置banner3（广告信息）信息
     */
    private fun setBanner3Info(helper: BaseViewHolder, item: ContentBean) {
        val cardNormalBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        with(cardNormalBottom) {
            setTitle(item.title)
            setDescription(item.description)
            setIconUrl(item.header.icon)
            setIconType(item.header.iconType == "round")//设置图标形状
            setMoreOperateVisible(false)
        }

        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        with(eliteView) {
            setImageUrl(item.image)
            setDailyVisible(false)
            setTranslateText(mContext.getString(R.string.advert))
        }
    }


    /**
     * 设置水平滚动卡片信息
     */
    private fun setHorizontalScrollCardInfo(helper: BaseViewHolder, itemList: MutableList<Content>) {
        val banner = helper.getView<Banner>(R.id.banner)
        with(banner) {
            setImageLoader(FrescoImageLoader())
            setImages(getHorizonTalCardUrl(itemList))
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            isAutoPlay(true)
            start()
            setDelayTime(5000)
            setOnBannerListener {
                //跳转到过滤界面
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(itemList[it].data.actionUrl)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                mContext.startActivity(intent)
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
        val eliteImageView = helper.getView<EliteImageView>(R.id.iv_image)
        val collectionRecycler = helper.getView<RecyclerView>(R.id.rv_collection_cover_recycler)
        val collectionCardCoverAdapter = CollectionCardCoverAdapter(item.itemList)
        collectionRecycler.isNestedScrollingEnabled = false
        collectionRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        collectionRecycler.adapter = collectionCardCoverAdapter
        eliteImageView.setImageUrl(item.header.cover)
        eliteImageView.setArrowVisible(true)

    }

    /**
     * 设置矩形卡片信息
     */
    private fun setSquareCollectionInfo(helper: BaseViewHolder, itemList: MutableList<Content>) {
        val squareRecycler = helper.getView<RecyclerView>(R.id.rv_square_recycler)
        val showAllContainer = helper.getView<RelativeLayout>(R.id.ll_more_container)
        squareRecycler.isNestedScrollingEnabled = false
        val squareCollectionAdapter = SquareCollectionAdapter(itemList)
        squareRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        squareRecycler.adapter = squareCollectionAdapter
        squareCollectionAdapter.onItemClickListener = OnItemClickListener { _, _, position ->
            //todo 跳转到分类详情

        }

        showAllContainer.setOnClickListener {
            //todo 查看更多
        }
    }

    /**
     * 设置长方形卡片信息
     */
    private fun setSquareCardInfo(helper: BaseViewHolder, item: ContentBean) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_simple_image)
        imageView.setImageURI(item.image)
        helper.setText(R.id.tv_title, item.title)
    }

    /**
     * 设置正方形卡片信息
     */
    private fun setRectangleCardInfo(helper: BaseViewHolder, item: ContentBean) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_simple_image)
        imageView.setImageURI(item.image)
        helper.setText(R.id.tv_title, item.title)

    }

    /**
     * 设置单视频信息
     */
    private fun setSingleVideoInfo(helper: BaseViewHolder, item: Content) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.cover.feed)
        helper.setText(R.id.tv_single_title, item.data.title)
        val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
        helper.setText(R.id.tv_single_desc, description)

        //设置label
        if (item.data.label != null) {
            helper.setGone(R.id.tv_label, true)
            helper.setText(R.id.tv_label, item.data.label?.text)
        } else {
            helper.setGone(R.id.tv_label, false)
        }
        //点击跳转到视频界面
        helper.itemView.setOnClickListener { VideoDetailActivity.start(mContext, item.data, data as ArrayList<Content>) }

    }
}