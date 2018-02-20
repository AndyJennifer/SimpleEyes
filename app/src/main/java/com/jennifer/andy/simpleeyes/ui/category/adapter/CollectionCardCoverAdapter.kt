package com.jennifer.andy.simpleeyes.ui.category.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ItemList
import com.jennifer.andy.simpleeyes.utils.TimeUtils


/**
 * Author:  andy.xwt
 * Date:    2017/11/6 16:14
 * Description:集合卡片适配器
 */

class CollectionCardCoverAdapter(data: MutableList<ItemList>) : BaseQuickAdapter<ItemList, BaseViewHolder>(R.layout.item_collection_card_cover, data) {

    override fun convert(helper: BaseViewHolder, item: ItemList) {
        val imageCover = helper.getView<SimpleDraweeView>(R.id.iv_image)
        val title = helper.getView<TextView>(R.id.tv_title)
        val desc = helper.getView<TextView>(R.id.tv_desc)
        helper.setGone(R.id.iv_daily, item.data.library == "DAILY")
        if (item.type != "actionCard") {//集合
            imageCover.setImageURI(item.data.cover.feed)
            title.text = item.data.title
            val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
            val elite = if (item.data.library == "DAILY")
                "   /   ${mContext.getString(R.string.elite)}" else ""
            desc.text = description + elite
        } else {//显示全部
            helper.setGone(R.id.tv_show_all, true)
            helper.setGone(R.id.ll_container, false)
            helper.addOnClickListener(R.id.tv_show_all)
        }
    }


}