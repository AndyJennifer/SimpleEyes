package com.jennifer.andy.simpleeyes.ui.follow.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

interface FollowView : LoadMoreView<AndyInfo> {

    fun loadFollowInfoSuccess(andyInfo: AndyInfo)

    fun refreshSuccess(andyInfo: AndyInfo)
}