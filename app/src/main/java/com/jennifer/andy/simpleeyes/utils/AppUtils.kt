package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * 获取App版本号
 *
 * @return App版本号
 */
fun Context.getAppVersionName(): String {
    val pm = packageManager
    val packageInfo = pm.getPackageInfo(packageName, 0)
    return packageInfo.versionName
}

/**
 * 获取App版本码
 *
 * @return App版本码
 */
fun Context.getAppVersionCode(): Int {
    val pm = packageManager
    val packageInfo = pm.getPackageInfo(packageName, 0)
    return packageInfo.versionCode
}
