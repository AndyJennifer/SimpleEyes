package com.jennifer.andy.simpleeyes.ui.home.adapter

import com.jennifer.andy.simpleeyes.net.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 17:04
 * Description:每日编辑精选
 */

class DailyEliteAdapter(data: MutableList<Content>) : BaseDataAdapter(data) {


    /**
     * 获取第一个日期位置
     */
    fun getCurrentDayPosition(): Int {
        for (i in 0 until mData.size) {
            if (getItemViewType(i) == TEXT_CARD_TYPE) {
                return i
            }
        }
        return 0
    }

}