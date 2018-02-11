package com.jennifer.andy.simpleeyes.ui.video.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ItemListBean
import com.jennifer.andy.simpleeyes.utils.TimeUtils


/**
 * Author:  andy.xwt
 * Date:    2018/2/11 14:28
 * Description:
 */

class VideoDetailAdapter(data: MutableList<ItemListBean>) : BaseQuickAdapter<ItemListBean, BaseViewHolder>(data) {

    val TEXT_CARD = "textCard"
    val VIDEO_SMALL_CARD = "videoSmallCard"

    val TEXT_CARD_TYPE = 0
    val VIDEO_SMALL_CARD_TYPE = 1


    init {
        multiTypeDelegate = object : MultiTypeDelegate<ItemListBean>() {
            override fun getItemType(andyInfoItem: ItemListBean?): Int {
                when (andyInfoItem?.type) {
                    TEXT_CARD -> return TEXT_CARD_TYPE
                    VIDEO_SMALL_CARD -> return VIDEO_SMALL_CARD_TYPE
                }
                return -1
            }
        }
        with(multiTypeDelegate) {
            registerItemType(TEXT_CARD_TYPE, R.layout.item_video_text_card)
            registerItemType(VIDEO_SMALL_CARD_TYPE, R.layout.item_video_samll_card)
        }

    }

    override fun convert(helper: BaseViewHolder?, item: ItemListBean) {
        when (helper?.itemViewType) {
            TEXT_CARD_TYPE -> setTextCardInfo(helper, item)
            VIDEO_SMALL_CARD_TYPE -> setVideoSmallCardInfo(helper, item)
        }
    }

    /**
     * 设置卡片信息
     */
    private fun setTextCardInfo(helper: BaseViewHolder, item: ItemListBean) {
        helper.setText(R.id.tv_text, item.data.text)

    }

    /**
     * 设置视频信息
     */
    private fun setVideoSmallCardInfo(helper: BaseViewHolder, item: ItemListBean) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.cover.feed)
        helper.setText(R.id.tv_title, item.data.title)
        val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
        helper.setText(R.id.tv_time, description)

    }


}