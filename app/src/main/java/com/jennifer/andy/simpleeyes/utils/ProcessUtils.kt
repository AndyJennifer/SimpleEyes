package com.jennifer.andy.simpleeyes.utils

import android.app.ActivityManager
import android.content.Context



/**
 * 获取当前进程名称
 */
fun getProcessName(context: Context): String? {
    var pid = android.os.Process.myPid()
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    activityManager.runningAppProcesses.forEach {
        if (it.pid == pid) {
            return it.processName
        }
    }
    return null
}