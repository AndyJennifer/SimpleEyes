package com.jennifer.andy.simpleeyes.ui.video.domain

import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-02-10 14:11
 * Description:
 */

class VideoRemoteDataSource {

    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String): Flowable<AndyInfo> = Api.getDefault().getRelatedVideo(id)


    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String): Flowable<ContentBean> = Api.getDefault().getVideoInfoById(id)
}