package com.jennifer.andy.simpleeyes.widget.pull

import android.content.res.TypedArray
import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2017/11/11 16:14
 * Description:下拉变焦 T 下拉变焦根布局
 */

interface PullToZoom<T : View> {

    /**
     * 获取变焦的view
     */
    fun getZoomView()

    /**
     * 获取头布局
     */
    fun getHeaderView()

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

    fun handleStyledAttributes(typedArray: TypedArray)
}