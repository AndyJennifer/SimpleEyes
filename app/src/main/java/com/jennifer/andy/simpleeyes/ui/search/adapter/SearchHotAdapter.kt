package com.jennifer.andy.simpleeyes.ui.search.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2018/4/8 10:16
 * Description:
 */

class SearchHotAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hot_search, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_text, item)
    }

}