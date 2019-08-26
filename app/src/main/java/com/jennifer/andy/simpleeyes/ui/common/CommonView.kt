package com.jennifer.andy.simpleeyes.ui.common

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2019-08-26 19:48
 * Description:
 */

interface CommonView: LoadMoreView<AndyInfo>{

    fun showLoadSuccess(itemList: MutableList<Content>)
}