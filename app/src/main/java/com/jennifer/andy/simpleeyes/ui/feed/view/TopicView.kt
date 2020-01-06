package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.base.view.LoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2018/7/31 15:38
 * Description:
 */

interface TopicView : LoadMoreView<AndyInfo> {

    fun showGetTopicInfoSuccess(itemList: MutableList<Content>)

}