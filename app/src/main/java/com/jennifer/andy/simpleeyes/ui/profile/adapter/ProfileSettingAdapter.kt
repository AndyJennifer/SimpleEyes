package com.jennifer.andy.simpleeyes.ui.profile.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2019/1/15 18:59
 * Description:
 */

class ProfileSettingAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_profile_setting, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_text, item)
    }

}