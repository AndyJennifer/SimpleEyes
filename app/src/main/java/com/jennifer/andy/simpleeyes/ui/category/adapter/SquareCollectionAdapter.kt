package com.jennifer.andy.simpleeyes.ui.category.adapter

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.font.TypefaceManager


/**
 * Author:  andy.xwt
 * Date:    2017/11/6 10:49
 * Description:矩形卡片适配器
 */

class SquareCollectionAdapter(data: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) : BaseQuickAdapter<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData, BaseViewHolder>(R.layout.item_square_collection, data) {

    override fun convert(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_simple_image)
        val coverView = helper.getView<FrameLayout>(R.id.fl_cover)
        if (item.type != "actionCard") {//分类
            imageView.setImageURI(item.data.image)
            helper.setText(R.id.tv_title, item.data.title)
            coverView.foreground = mContext?.resources?.getDrawable(R.drawable.selector_item_square_foreground)
        } else {//显示全部
            imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.shape_show_all_border))
            helper.setText(R.id.tv_title, item.data.text)
            helper.setTextColor(R.id.tv_title, mContext.resources.getColor(R.color.black))
            helper.setTypeface(R.id.tv_title, TypefaceManager.getTypeFace(FontType.NORMAL))
            coverView.foreground = null

        }
    }

}