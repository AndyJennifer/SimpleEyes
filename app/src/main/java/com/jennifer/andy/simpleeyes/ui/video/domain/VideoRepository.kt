package com.jennifer.andy.simpleeyes.ui.video.domain


/**
 * Author:  andy.xwt
 * Date:    2020-02-10 14:13
 * Description:
 */

class VideoRepository(private val videoRemoteDataSource: VideoRemoteDataSource) {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) = videoRemoteDataSource.getRelatedVideoInfo(id)


    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String) = videoRemoteDataSource.getVideoInfoById(id)
}