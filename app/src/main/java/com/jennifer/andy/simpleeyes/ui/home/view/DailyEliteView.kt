package com.jennifer.andy.simpleeyes.ui.home.view

import com.jennifer.andy.simpleeyes.net.entity.Content
import com.jennifer.andy.simpleeyes.base.view.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:13
 * Description:
 */

interface DailyEliteView : LoadMoreView<MutableList<Content>> {

    fun showGetDailySuccess(content: MutableList<Content>)

    fun showRefreshSuccess(content: MutableList<Content>)

}