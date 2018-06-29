package com.jennifer.andy.simpleeyes.ui.category.view

import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:13
 * Description:
 */

interface DailyEliteView : LoadMoreView<MutableList<Content>> {
    fun showGetDailySuccess(it: MutableList<Content>)
}