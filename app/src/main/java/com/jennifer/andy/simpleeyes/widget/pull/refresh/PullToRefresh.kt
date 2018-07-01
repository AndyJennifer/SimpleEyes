package com.jennifer.andy.simpleeyes.widget.pull.refresh


/**
 * Author:  andy.xwt
 * Date:    2018/6/15 10:12
 * Description:
 */

interface PullToRefresh<T> {

    /**
     * 设置刷新View
     */
    fun initRefreshView(): PullRefreshView?


    /**
     * 是否正在刷新
     */
    fun isRefreshing(): Boolean

    /**
     * 刷新完毕
     */
    fun refreshComplete()

    /**
     * 下拉刷新是否可用
     */
    fun isPullToRefreshEnabled(): Boolean

    /**
     * 获取根布局
     */
    fun getRootView(): T
}