package com.jennifer.andy.simpleeyes.ui.category.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.image.FrescoImageLoader
import com.jennifer.andy.simpleeyes.widget.EliteImageView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


/**
 * Author:  andy.xwt
 * Date:    2017/10/27 16:35
 * Description: 主页信息适配器
 */

class CategoryAdapter(data: MutableList<AndyInfo.ItemListBeanX>) : BaseQuickAdapter<AndyInfo.ItemListBeanX, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    companion object {

        private val VIDEO_BANNER_TYPE = 0
        private val VIDEO_FOLLOW_CARD_TYPE = 1
        private val VIDEO_HORIZONTAL_CARD_TYPE = 2
        private val VIDEO_COLLECTION_WITH_COVER_TYPE = 3

        private val VIDEO_BANNER = "banner"
        private val VIDEO_FOLLOW_CARD = "followCard"
        private val VIDEO_HORIZONTAL_CARD = "horizontalScrollCard"
        private val VIDEO_COLLECTION_WITH_COVER = "videoCollectionWithCover"
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<AndyInfo.ItemListBeanX>() {
            override fun getItemType(andyInfoItem: AndyInfo.ItemListBeanX?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return VIDEO_BANNER_TYPE
                    VIDEO_FOLLOW_CARD -> return VIDEO_FOLLOW_CARD_TYPE
                    VIDEO_HORIZONTAL_CARD -> return VIDEO_HORIZONTAL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_COVER -> return VIDEO_COLLECTION_WITH_COVER_TYPE
                }
                return VIDEO_FOLLOW_CARD_TYPE
            }
        }
        multiTypeDelegate.registerItemType(VIDEO_BANNER_TYPE, R.layout.item_video_banner)
        multiTypeDelegate.registerItemType(VIDEO_FOLLOW_CARD_TYPE, R.layout.item_video_follow_card)
        multiTypeDelegate.registerItemType(VIDEO_HORIZONTAL_CARD_TYPE, R.layout.item_horizontal_scroll_card)
        multiTypeDelegate.registerItemType(VIDEO_COLLECTION_WITH_COVER_TYPE, R.layout.item_collection_with_cover)
    }

    override fun convert(helper: BaseViewHolder?, item: AndyInfo.ItemListBeanX) {
        when (helper?.itemViewType) {
            VIDEO_BANNER_TYPE -> setVideoBannerInfo(helper, item)
            VIDEO_FOLLOW_CARD_TYPE -> setVideoFollowCardInfo(helper, item)
            VIDEO_HORIZONTAL_CARD_TYPE -> setHorizontalCardInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_WITH_COVER_TYPE -> setCollectionCardInfo(helper, item.data.itemList)
        }
    }


    /**
     * 设置视频banner信息
     */
    private fun setVideoBannerInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.content.data.cover.feed)
    }

    /**
     * 设置单视频卡片信息
     */
    private fun setVideoFollowCardInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {
        val info = item.data
        helper.setText(R.id.tv_title, info.header.title)
        helper.setText(R.id.tv_desc, info.header.description)
        val iconView = helper.getView<SimpleDraweeView>(R.id.iv_source)
        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        iconView.setImageURI(info.header.icon)
        eliteView.setImageUrl(info.content.data.cover.feed)
        //设置图标形状
        if (info.header.iconType == "round") iconView.hierarchy.roundingParams = RoundingParams.asCircle() else iconView.hierarchy.roundingParams?.roundAsCircle = false

        //设置精选显示
        if (info.content.data.library == "DAILY") eliteView.setDailyVisible(true) else eliteView.setDailyVisible(false)

    }

    /**
     * 设置水平卡片信息
     */
    private fun setHorizontalCardInfo(helper: BaseViewHolder, itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) {
        val banner = helper.getView<Banner>(R.id.banner)
        banner.setImageLoader(FrescoImageLoader())
        banner.setImages(getHorizonTalCardUrl(itemList))
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.CENTER)
        banner.isAutoPlay(true)
        banner.start()
        banner.setDelayTime(5000)
        banner.setOnBannerListener {
            //点击跳转
        }
    }

    /**
     * 获取水平卡片图片地址
     */
    private fun getHorizonTalCardUrl(itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) = itemList.map { it.data.image }


    /**
     * 设置集合卡片信息
     */
    private fun setCollectionCardInfo(helper: BaseViewHolder, itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) {


    }
}