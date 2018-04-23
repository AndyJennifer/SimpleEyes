package com.jennifer.andy.simpleeyes.ui.category.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.utils.TimeUtils


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 17:04
 * Description:每日编辑精选
 */

class DailyEliteAdapter(data: MutableList<Content>) : BaseQuickAdapter<Content, BaseViewHolder>(data) {


    val BANNER_TYPE = 0
    val VIDEO_TYPE = 1
    val TEXT_CARD_TYPE = 2

    val VIDEO_BANNER = "banner"
    val VIDEO = "video"
    val TEXT_CARD = "textCard"

    init {
        multiTypeDelegate = object : MultiTypeDelegate<Content>() {
            override fun getItemType(andyInfoItem: Content?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return BANNER_TYPE
                    VIDEO -> return VIDEO_TYPE
                    TEXT_CARD -> return TEXT_CARD_TYPE
                }
                return VIDEO_TYPE
            }
        }
        with(multiTypeDelegate) {
            registerItemType(BANNER_TYPE, R.layout.layout_card_banner)
            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
            registerItemType(TEXT_CARD_TYPE, R.layout.layout_single_text)
        }

    }

    override fun convert(helper: BaseViewHolder, item: Content) {
        when (helper.itemViewType) {
            BANNER_TYPE -> setBannerInfo(helper, item)
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
            TEXT_CARD_TYPE -> setSingleText(helper, item)
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

    private fun setSingleText(helper: BaseViewHolder, item: Content) {
        helper.setText(R.id.tv_text, item.data.text)
    }

}