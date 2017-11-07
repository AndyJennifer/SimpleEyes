package com.jennifer.andy.simpleeyes.ui.category.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo


/**
 * Author:  andy.xwt
 * Date:    2017/11/6 16:14
 * Description:集合卡片适配器
 */

class CollectionCardCoverAdapter(data: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) : BaseQuickAdapter<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData, BaseViewHolder>(R.layout.item_collection_card_cover, data) {

    override fun convert(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData) {
        val imageCover = helper.getView<SimpleDraweeView>(R.id.iv_image)
        val title = helper.getView<TextView>(R.id.tv_title)
        helper.setGone(R.id.iv_daily, item.data.library == "DAILY")
        if (item.type != "actionCard") {//集合
            imageCover.setImageURI(item.data.cover.feed)
            //todo 拼接信息
            title.text = item.data.title
        } else {//显示全部
        }
    }


}