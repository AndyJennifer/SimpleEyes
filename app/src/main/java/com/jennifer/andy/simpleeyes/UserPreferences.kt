package com.jennifer.andy.simpleeyes

import android.content.Context
import android.content.SharedPreferences


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 09:34
 * Description:用户配置信息
 */

object UserPreferences {

    private const val NAME = "andy."
    private const val KEY_IS_USER_LOGIN = "is_user_login"

    /**
     * 保存用户是否登录配置
     */
    fun saveUserIsLogin(isUserLogin: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(KEY_IS_USER_LOGIN, isUserLogin)
        editor.apply()
    }

    /**
     * 获取用户是否登录
     */
    fun getUserIsLogin() = getSharedPreferences().getBoolean(KEY_IS_USER_LOGIN, false)


    private fun getSharedPreferences(): SharedPreferences {
        return AndyApplication.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}