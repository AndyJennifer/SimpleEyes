package com.jennifer.andy.base.utils

import com.jennifer.andy.base.application.BaseApplication
import com.jennifer.andy.base.ext.toast


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 22:43
 * Description:
 */

fun toast(value: String) = toast { value }

fun toast(value: () -> String) = BaseApplication.INSTANCE.toast(value)