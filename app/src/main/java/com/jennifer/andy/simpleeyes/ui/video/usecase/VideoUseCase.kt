package com.jennifer.andy.simpleeyes.ui.video.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.video.domain.VideoRepository


/**
 * Author:  andy.xwt
 * Date:    2020-02-10 14:16
 * Description:
 */

class VideoUseCase(private val videoRepository: VideoRepository) {


    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) =
            videoRepository.getRelatedVideoInfo(id)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String) =
            videoRepository.getVideoInfoById(id)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

}