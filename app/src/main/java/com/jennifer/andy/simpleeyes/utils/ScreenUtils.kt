package com.jennifer.andy.simpleeyes.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Author:  andy.xwt
 * Date:    2017/11/20 14:05
 * Description:屏幕相关工具类
 */

object ScreenUtils {

    /**
     * 获取屏幕的宽度
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    /**
     * 获取屏幕的高度
     */
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }
}