package com.jennifer.andy.simplemusic.ui

import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:09
 * Description:基础view
 */

interface BaseView {
    /**
     * 显示正在加载
     */
    fun showLoading()

    /**
     * 显示网络异常
     */
    fun showNetError(onClickListener: View.OnClickListener)

    /**
     * 显示空界面
     */
    fun showEmpty(onClickListener: View.OnClickListener)
}