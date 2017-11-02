package com.jennifer.andy.simpleeyes.ui.category.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo


/**
 * Author:  andy.xwt
 * Date:    2017/10/27 16:35
 * Description: 主页信息适配器
 */

class CategoryAdapter(data: MutableList<AndyInfo.ItemListBeanX>) : BaseQuickAdapter<AndyInfo.ItemListBeanX, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    private val VIDEO_BANNER_TYPE = 0
    private val VIDEO_FOLLOW_CARD_TYPE = 1

    private val VIDEO_BANNER = "banner"
    private val VIDEO_FOLLOW_CARD = "followCard"

    init {
        multiTypeDelegate = object : MultiTypeDelegate<AndyInfo.ItemListBeanX>() {
            override fun getItemType(andyInfoItem: AndyInfo.ItemListBeanX?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return VIDEO_BANNER_TYPE
                    VIDEO_FOLLOW_CARD -> return VIDEO_FOLLOW_CARD_TYPE
                }
                return VIDEO_FOLLOW_CARD_TYPE
            }
        }
        multiTypeDelegate.registerItemType(VIDEO_BANNER_TYPE, R.layout.item_video_banner)
        multiTypeDelegate.registerItemType(VIDEO_FOLLOW_CARD_TYPE, R.layout.item_video_follow_card)
    }

    override fun convert(helper: BaseViewHolder?, item: AndyInfo.ItemListBeanX) {
        when (helper?.itemViewType) {
            VIDEO_BANNER_TYPE -> setVideoBannerInfo(helper, item)
            VIDEO_FOLLOW_CARD_TYPE -> setVideoFollowCardInfo(helper, item)
        }
    }

    /**
     * 设置视频banner信息
     */
    private fun setVideoBannerInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {

    }

    /**
     * 设置视频卡片信息
     */
    private fun setVideoFollowCardInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {
        val info = item.data
        helper.setText(R.id.tv_title, info.header.title)
        helper.setText(R.id.tv_desc, info.header.description)
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        val sourceImageView = helper.getView<SimpleDraweeView>(R.id.iv_source)
        //todo 修改
        imageView.setImageURI(info.content.data.cover?.feed)
    }
}