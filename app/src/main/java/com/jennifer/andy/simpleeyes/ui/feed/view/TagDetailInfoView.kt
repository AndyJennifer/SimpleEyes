package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:30
 * Description:
 */

interface TagDetailInfoView : LoadMoreView<AndyInfo> {

    fun showGetTabInfoSuccess(andyInfo: AndyInfo)

}