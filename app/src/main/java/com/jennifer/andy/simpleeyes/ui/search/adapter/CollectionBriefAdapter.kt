package com.jennifer.andy.simpleeyes.ui.search.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.utils.TimeUtils


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 11:01
 * Description:
 */

class CollectionBriefAdapter(data: MutableList<Content>) : BaseQuickAdapter<Content, BaseViewHolder>(R.layout.item_collection_brief, data) {

    override fun convert(helper: BaseViewHolder, item: Content) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.cover.feed)
        helper.setText(R.id.tv_title, item.data.title)
        val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
        helper.setText(R.id.tv_desc, description)
    }
}