package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * 获取设备尺寸密度
 */
fun getDensity(context: Context) = context.resources.displayMetrics.density

/**
 * 获取设备收缩密度
 */
fun getScaleDensity(context: Context) = context.resources.displayMetrics.scaledDensity

/**
 * dp转px
 */
fun dip2px(context: Context, dipValue: Float) = (dipValue * getDensity(context) + 0.5f).toInt()

/**
 * px转dp
 */
fun px2dip(context: Context, pxValue: Float) = ((pxValue / getDensity(context)) + 0.5f).toInt()

/**
 * sp转px
 */
fun sp2px(context: Context, spValue: Float) = ((spValue * getScaleDensity(context)) + 0.5f).toInt()

/**
 * px转sp
 */
fun px2sp(context: Context, pxValue: Float) = (pxValue / getScaleDensity(context) + 0.5f).toInt()

