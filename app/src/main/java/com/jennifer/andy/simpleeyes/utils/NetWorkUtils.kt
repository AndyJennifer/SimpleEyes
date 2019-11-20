package com.jennifer.andy.simpleeyes.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * 判断当前网络时候连接
 */
fun Context.isNetWorkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo//返回当前网络的详细信息
    return networkInfo.isAvailable
}
