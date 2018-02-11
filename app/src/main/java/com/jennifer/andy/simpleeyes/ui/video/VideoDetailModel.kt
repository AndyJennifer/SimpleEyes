package com.jennifer.andy.simpleeyes.ui.video

import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel


/**
 * Author:  andy.xwt
 * Date:    2018/1/5 15:29
 * Description:
 */

class VideoDetailModel : BaseModel {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) = Api.getDefault().getRelatedVido(id).compose(RxHelper.handleResult())
}