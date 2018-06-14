package com.jennifer.andy.simpleeyes.ui.category.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.image.FrescoImageLoader
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 17:04
 * Description:每日编辑精选
 */

class DailyEliteAdapter(data: MutableList<Content>) : BaseQuickAdapter<Content, BaseViewHolder>(data) {

    companion object {
        const val BANNER_TYPE = 0
        const val VIDEO_TYPE = 1
        const val TEXT_CARD_TYPE = 2
        const val HORIZONTAL_SCROLL_CARD_TYPE = 3
        const val VIDEO_BANNER = "banner"
        const val VIDEO = "video"
        const val TEXT_CARD = "textCard"
        const val HORIZONTAL_CARD = "horizontalScrollCard"
    }

    init {
        multiTypeDelegate = object : MultiTypeDelegate<Content>() {
            override fun getItemType(andyInfoItem: Content?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return BANNER_TYPE
                    VIDEO -> return VIDEO_TYPE
                    TEXT_CARD -> return TEXT_CARD_TYPE
                    HORIZONTAL_CARD -> return HORIZONTAL_SCROLL_CARD_TYPE
                }
                return VIDEO_TYPE
            }
        }
        with(multiTypeDelegate) {
            registerItemType(BANNER_TYPE, R.layout.layout_card_banner)
            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
            registerItemType(TEXT_CARD_TYPE, R.layout.layout_single_text)
            registerItemType(HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_horizontal_scroll_card)
        }

    }

    override fun convert(helper: BaseViewHolder, item: Content) {
        when (helper.itemViewType) {
            BANNER_TYPE -> setBannerInfo(helper, item)
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            TEXT_CARD_TYPE -> setSingleText(helper, item)
            HORIZONTAL_SCROLL_CARD_TYPE -> setHorizontalScrollCardInfo(helper, item)
        }
    }

    /**
     * 设置视频banner信息
     */
    private fun setBannerInfo(helper: BaseViewHolder, item: Content) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.image)
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
    }

    /**
     * 设置文字信息
     */
    private fun setSingleText(helper: BaseViewHolder, item: Content) {
        helper.setText(R.id.tv_text, item.data.text)
    }


    /**
     * 设置水平滚动卡片信息
     */
    private fun setHorizontalScrollCardInfo(helper: BaseViewHolder, item: Content) {
        val itemList = item.data.itemList
        val banner = helper.getView<Banner>(R.id.banner)
        with(banner) {
            setImageLoader(FrescoImageLoader())
            setImages(getHorizonTalCardUrl(itemList))
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            isAutoPlay(true)
            start()
            setDelayTime(5000)

        }
    }

    /**
     * 获取水平卡片图片地址
     */
    private fun getHorizonTalCardUrl(itemList: MutableList<Content>) = itemList.map { it.data.image }

}