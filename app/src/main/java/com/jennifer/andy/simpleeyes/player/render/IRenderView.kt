package com.jennifer.andy.simpleeyes.player.render

import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import tv.danmaku.ijk.media.player.IMediaPlayer


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 22:44
 * Description:
 */

interface IRenderView {

    companion object {
        const val AR_ASPECT_FIT_PARENT = 0 // 适应父布局模式
        const val AR_ASPECT_FILL_PARENT = 1 //填充父布局模式
        const val AR_ASPECT_WRAP_CONTENT = 2 //包裹模式
        const val AR_MATCH_PARENT = 3 //匹配父布局模式
        const val AR_16_9_FIT_PARENT = 4 //16:9适配屏幕
        const val AR_4_3_FIT_PARENT = 5 //4:3适配屏幕
    }

    /**
     * 获取当前view
     */
    fun getView(): View?

    /**
     * 是否需要等待重新测量
     */
    fun shouldWaitForResize(): Boolean

    /**
     * 设置视屏的宽高
     */
    fun setVideoSize(videoWidth: Int, videoHeight: Int)

    /**
     * 设置视频采样方向比例
     */
    fun setVideoSampleAspectRatio(videoSarNum: Int, videoSarDen: Int)

    /**
     * 设置视频旋转角度
     */
    fun setVideoRotation(degree: Int)

    /**
     * 设置方向比例
     */
    fun setAspectRatio(aspectRatio: Int)

    /**
     * 设置渲染回调
     */
    fun addRenderCallback(callback: IRenderCallback)

    /**
     * 移除渲染回调
     */
    fun removeRenderCallback(callback: IRenderCallback)

    /**
     * surfaceHolder管理相关接口
     */
    interface ISurfaceHolder {
        /**
         * 绑定当前媒体播放器
         */
        fun bindToMediaPlayer(mp: IMediaPlayer?)

        /**
         * 获取当前渲染的view
         */
        val renderView: IRenderView

        /**
         * 获取当前surfaceHolder
         */
        val surfaceHolder: SurfaceHolder?

        /**
         * 打开surface
         */
        fun openSurface(): Surface?

        /**
         * 获取SurfaceTexture
         */
        val surfaceTexture: SurfaceTexture?
    }

    interface IRenderCallback {
        fun onSurfaceCreated(holder: ISurfaceHolder, width: Int, height: Int)
        fun onSurfaceChanged(holder: ISurfaceHolder, format: Int, width: Int, height: Int)
        fun onSurfaceDestroyed(holder: ISurfaceHolder)
    }
}
