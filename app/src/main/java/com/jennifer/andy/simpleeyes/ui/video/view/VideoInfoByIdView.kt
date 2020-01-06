package com.jennifer.andy.simpleeyes.ui.video.view

import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.base.view.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/7/23 14:31
 * Description:
 */

interface VideoInfoByIdView : BaseView {

    fun getVideoInfoSuccess(contentBean: ContentBean)
}