package com.jennifer.andy.simpleeyes.ui.category.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.widget.CardNormalBottom
import com.jennifer.andy.simpleeyes.widget.EliteImageView


/**
 * Author:  andy.xwt
 * Date:    2017/11/6 16:14
 * Description:集合卡片适配器
 */

class CollectionCardCoverAdapter(data: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) : BaseQuickAdapter<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData, BaseViewHolder>(R.layout.item_collection_card_cover, data) {

    override fun convert(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData) {
        var imageCover = helper.getView<EliteImageView>(R.id.iv_collection_cover_image)
        val cardBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        if (item.type != "actionCard") {//集合
            imageCover.setDailyVisible(item.data.library == "DAILY")
            imageCover.setImageUrl(item.data.cover.feed)
            cardBottom.setIconVisible(false)
            cardBottom.setLineVisible(false)
            cardBottom.setMoreOperateVisible(false)
            //todo 拼接信息
            cardBottom.setTitle(item.data.title)
            cardBottom.visibility = View.VISIBLE
        } else {//显示全部
            cardBottom.visibility = View.GONE

        }
        //todo 这里改一下吧，感觉不能复用
    }


}