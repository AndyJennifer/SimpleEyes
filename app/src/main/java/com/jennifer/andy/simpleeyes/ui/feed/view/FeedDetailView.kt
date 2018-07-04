package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:30
 * Description:
 */

interface FeedDetailView : BaseView {

    fun showGetTabInfoSuccess(andyInfo: AndyInfo)

}