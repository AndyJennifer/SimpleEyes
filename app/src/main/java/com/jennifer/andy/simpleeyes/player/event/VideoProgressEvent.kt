package com.jennifer.andy.simpleeyes.player.event


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 10:02 PM
 * Description: 视频播放进度事件
 */

data class VideoProgressEvent(
        var duration: Int,//总时间
        var currentPosition: Int,//当前播放时间
        var progress: Int,//第一进度（播放进度）
        var secondaryProgress: Int//第二进度（下载进度）
)
