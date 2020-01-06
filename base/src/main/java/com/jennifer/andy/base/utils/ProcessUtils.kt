package com.jennifer.andy.base.utils

import android.app.ActivityManager
import android.content.Context


/**
 * 获取当前进程名称
 */
fun Context.getProcessName(): String? {
    val pid = android.os.Process.myPid()
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.runningAppProcesses.forEach {
        if (it.pid == pid) {
            return it.processName
        }
    }
    return null
}