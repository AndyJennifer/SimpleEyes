package com.jennifer.andy.simpleeyes.utils

import android.content.Context


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 15:06
 * Description:应用工具类
 */

object AppUtils {

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    @JvmStatic
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
    @JvmStatic
    fun getAppVersionCode(context: Context): Int {
        val pm = context.packageManager
        val packageInfo = pm.getPackageInfo(context.packageName, 0)
        return packageInfo.versionCode
    }
}