package com.jennifer.andy.simpleeyes.widget.pull.zoom

import android.content.res.TypedArray
import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2017/11/11 16:14
 * Description:下拉变焦 T 下拉变焦根布局
 */

interface PullToZoom<out T : View> {

    /**
     * 获取变焦的view
     */
    fun getZoomView(): View?

    /**
     * 获取头布局
     */
    fun getHeaderView(): View?

    /**
     * 获取下拉变焦根布局
     */
    fun getPullRootView(): T

    /**
     * 下拉变焦是否可用
     */
    fun isPullToZoomEnabled(): Boolean

    /**
     * 是否正在变焦
     */
    fun isZooming(): Boolean

    /**
     * 是否存在视差
     */
    fun isParallax(): Boolean

    /**
     * 是否隐藏头部
     */
    fun isHideHeader(): Boolean

    /**
     * 处理配置的zoomView与headerView
     */
    fun handleStyledAttributes(typedArray: TypedArray)
}