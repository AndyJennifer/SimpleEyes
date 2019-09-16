package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * 获取App版本号
 *
 * @return App版本号
 */
fun getAppVersionName(context: Context): String {
    val pm = context.packageManager
    val packageInfo = pm.getPackageInfo(context.packageName, 0)
    return packageInfo.versionName
}

/**
 * 获取App版本码
 *
 * @return App版本码
 */
fun getAppVersionCode(context: Context): Int {
    val pm = context.packageManager
    val packageInfo = pm.getPackageInfo(context.packageName, 0)
    return packageInfo.versionCode
}
