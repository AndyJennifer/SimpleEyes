package com.jennifer.andy.base.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build


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
@TargetApi(Build.VERSION_CODES.P)
fun Context.getAppVersionCode(): Long {
    val pm = packageManager
    val packageInfo = pm.getPackageInfo(packageName, 0)
    return packageInfo.longVersionCode
}
