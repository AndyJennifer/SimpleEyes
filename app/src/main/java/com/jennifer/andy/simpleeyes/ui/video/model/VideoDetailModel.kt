package com.jennifer.andy.simpleeyes.ui.video.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxThreadHelper
import com.jennifer.andy.simpleeyes.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2018/1/5 15:29
 * Description:
 */

class VideoDetailModel : BaseModel {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String): Observable<AndyInfo> =
            Api.getDefault()
                    .getRelatedVideo(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())

    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String): Observable<ContentBean> =
            Api.getDefault()
                    .getVideoInfoById(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())
}