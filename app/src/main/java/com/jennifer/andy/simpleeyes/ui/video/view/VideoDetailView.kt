package com.jennifer.andy.simpleeyes.ui.video.view

import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:56
 * Description:
 */

interface VideoDetailView : BaseView {

    /**
     * 获取相关视频成功
     */
    fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>)

    /**
     * 获取相关视屏失败
     */
    fun getRelatedVideoFail()

}