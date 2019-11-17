package com.jennifer.andy.simpleeyes.utils

import android.content.Context
import android.widget.Toast
import com.jennifer.andy.simpleeyes.AndyApplication


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 22:43
 * Description:
 */


fun toast(value: () -> String) = AndyApplication.INSTANCE.toast(value)

inline fun toast(value: String) = toast { value }

fun Context.toast(value: () -> String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, value(), duration).show()
}
