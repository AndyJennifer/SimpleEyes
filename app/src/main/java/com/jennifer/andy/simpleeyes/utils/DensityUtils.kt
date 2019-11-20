package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * 获取设备尺寸密度
 */
fun Context.getDensity() = resources.displayMetrics.density

/**
 * 获取设备收缩密度
 */
fun Context.getScaleDensity() = resources.displayMetrics.scaledDensity

/**
 * dp转px
 */
fun Context.dip2px(dipValue: Float) = (dipValue * getDensity() + 0.5f).toInt()

/**
 * px转dp
 */
fun Context.px2dip(pxValue: Float) = ((pxValue / getDensity()) + 0.5f).toInt()

/**
 * sp转px
 */
fun Context.sp2px(spValue: Float) = ((spValue * getScaleDensity()) + 0.5f).toInt()

/**
 * px转sp
 */
fun Context.px2sp(pxValue: Float) = (pxValue / getScaleDensity() + 0.5f).toInt()

