package com.jennifer.andy.base.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.fragment.app.Fragment


/**
 * 获取屏幕的宽度
 */
fun Context.getScreenWidth(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * 获取屏幕的宽度
 */
fun Fragment.getScreenWidth(): Int {
    val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}


/**
 * 获取屏幕的高度
 */
fun Context.getScreenHeight(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

/**
 * 获取屏幕的高度
 */
fun Fragment.getScreenHeight(): Int {
    val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}
