package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:53
 * Description:
 */

interface FeedView : BaseView {

    fun loadTabSuccess(tabInfo: TabInfo)

}