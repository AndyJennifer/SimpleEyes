package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.base.view.BaseView
import com.jennifer.andy.simpleeyes.entity.TabInfo


/**
 * Author:  andy.xwt
 * Date:    2018/7/26 18:06
 * Description:
 */

interface RankListView : BaseView {

    fun loadTabSuccess(tabInfo: TabInfo)

}