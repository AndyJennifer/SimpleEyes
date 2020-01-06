package com.jennifer.andy.simpleeyes.ui.video.model

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.model.BaseModel
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2018/1/5 15:29
 * Description:
 */

class VideoDetailModel : BaseModel {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String): Flowable<AndyInfo> =
            Api.getDefault()
                    .getRelatedVideo(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchFlowableThread())

    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String): Flowable<ContentBean> =
            Api.getDefault()
                    .getVideoInfoById(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchFlowableThread())
}