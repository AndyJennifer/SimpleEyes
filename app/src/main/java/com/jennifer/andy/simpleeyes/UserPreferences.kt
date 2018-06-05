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
    private const val KEY_IS_FIRST_LOGIN = "is_first_login"

    /**
     * 保存用户是否登录配置
     */
    fun saveUserIsLogin(isUserLogin: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(KEY_IS_USER_LOGIN, isUserLogin)
        editor.apply()
    }

    /**
     * 保存用户是否是第一次登录
     */
    fun saveUserIsFirstLogin(isFirstLogin: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(KEY_IS_FIRST_LOGIN, isFirstLogin)
        editor.apply()
    }

    /**
     * 获取用户是否登录
     */
    fun getUserIsLogin() = getSharedPreferences().getBoolean(KEY_IS_USER_LOGIN, false)

    /**
     * 获取用户是否是第一次登录
     */
    fun getUserIsFirstLogin() = getSharedPreferences().getBoolean(KEY_IS_FIRST_LOGIN, true)


    private fun getSharedPreferences(): SharedPreferences {
        return AndyApplication.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}