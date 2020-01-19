package com.jennifer.andy.simpleeyes.ui.common

import com.jennifer.andy.simpleeyes.base.view.LoadMoreView
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.Content


/**
 * Author:  andy.xwt
 * Date:    2019-08-26 19:48
 * Description:
 */

interface CommonView: LoadMoreView<AndyInfo> {

    fun showLoadSuccess(itemList: MutableList<Content>)
}