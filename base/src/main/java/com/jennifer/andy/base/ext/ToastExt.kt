package com.jennifer.andy.base.ext

import android.content.Context
import android.os.Handler
import android.widget.Toast


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 22:43
 * Description:
 */


inline fun Context.toast(crossinline value: () -> String, duration: Int = Toast.LENGTH_SHORT) {
    Handler(mainLooper).post { Toast.makeText(this, value(), duration).show() }
}
