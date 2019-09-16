package com.jennifer.andy.simpleeyes.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * 获取屏幕的宽度
 */
fun getScreenWidth(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * 获取屏幕的高度
 */
fun getScreenHeight(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}
