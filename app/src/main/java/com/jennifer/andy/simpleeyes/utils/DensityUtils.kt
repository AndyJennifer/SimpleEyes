package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * Author:  andy.xwt
 * Date:    2017/10/23 12:44
 * Description:尺寸工具类
 */

object DensityUtils {


    /**
     * 获取设备尺寸密度
     */
    @JvmStatic
    fun getDensity(context: Context) = context.resources.displayMetrics.density

    /**
     * 获取设备收缩密度
     */
    @JvmStatic
    fun getScaleDensity(context: Context) = context.resources.displayMetrics.scaledDensity

    /**
     * dp转px
     */
    @JvmStatic
    fun dip2px(context: Context, dipValue: Float) = (dipValue * getDensity(context) + 0.5f).toInt()

    /**
     * px转dp
     */
    @JvmStatic
    fun px2dip(context: Context, pxValue: Float) = ((pxValue / getDensity(context)) + 0.5f).toInt()

    /**
     * sp转px
     */
    @JvmStatic
    fun sp2px(context: Context, spValue: Float) = ((spValue * getScaleDensity(context)) + 0.5f).toInt()

    /**
     * px转sp
     */
    @JvmStatic
    fun px2sp(context: Context, pxValue: Float) = (pxValue / getScaleDensity(context) + 0.5f).toInt()

}