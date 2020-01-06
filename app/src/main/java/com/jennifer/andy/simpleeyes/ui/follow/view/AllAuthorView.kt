package com.jennifer.andy.simpleeyes.ui.follow.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.base.view.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2019-07-09 18:22
 * Description:
 */

interface AllAuthorView : LoadMoreView<AndyInfo> {

    fun loadAllAuthorSuccess(it: AndyInfo)

}