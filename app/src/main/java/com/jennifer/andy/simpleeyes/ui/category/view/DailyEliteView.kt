package com.jennifer.andy.simpleeyes.ui.category.view

import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:13
 * Description:
 */

interface DailyEliteView : BaseView {
    fun showGetDailySuccess(it: MutableList<Content>)
}