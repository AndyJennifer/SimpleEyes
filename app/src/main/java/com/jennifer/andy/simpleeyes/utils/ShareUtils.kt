package com.jennifer.andy.simpleeyes.utils

import android.content.SharedPreferences


/**
 * Author:  andy.xwt
 * Date:    2019-08-28 20:01
 * Description:使用androidx.core:core-ktx中的工具类
 */

inline fun SharedPreferences.edit(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit
) {
    val editor = edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}
