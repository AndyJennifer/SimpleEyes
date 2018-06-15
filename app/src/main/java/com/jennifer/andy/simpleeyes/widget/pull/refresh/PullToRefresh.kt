package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2018/6/15 10:12
 * Description:
 */

interface PullToRefresh<T> {

    /**
     * 获取刷新view
     */
    fun getRefreshView(): View?


    /**
     * 是否正在刷新
     */
    fun isRefreshing(): Boolean


    /**
     * 下拉刷新是否可用
     */
    fun isPullToRefreshEnabled(): Boolean
}