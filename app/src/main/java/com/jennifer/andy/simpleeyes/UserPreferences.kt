package com.jennifer.andy.simpleeyes

import android.content.Context
import android.content.SharedPreferences
import com.jennifer.andy.simpleeyes.utils.edit


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 09:34
 * Description:用户配置信息
 */

object UserPreferences {

    private const val NAME = "andy."
    private const val KEY_IS_USER_LOGIN = "is_user_login"
    private const val KEY_IS_FIRST_LOGIN = "is_first_login"
    private const val KEY_IS_SHOW_USER_ANIM = "key_is_show_user_anim"

    /**
     * 保存用户是否登录配置
     */
    fun saveUserIsLogin(isUserLogin: Boolean) {
        getSharedPreferences().edit {
            putBoolean(KEY_IS_USER_LOGIN, isUserLogin)
        }
    }

    /**
     * 是否像用户展示过动画
     */
    fun saveShowUserAnim(isUserLogin: Boolean) {
        getSharedPreferences().edit {
            putBoolean(KEY_IS_SHOW_USER_ANIM, isUserLogin)
        }
    }

    /**
     * 保存用户是否是第一次登录
     */
    fun saveUserIsFirstLogin(isFirstLogin: Boolean) {
        getSharedPreferences().edit {
            putBoolean(KEY_IS_FIRST_LOGIN, isFirstLogin)
        }
    }

    /**
     * 获取用户是否登录
     */
    fun getUserIsLogin() = getSharedPreferences().getBoolean(KEY_IS_USER_LOGIN, false)

    /**
     * 是否像用户展示过动画
     */
    fun getShowUserAnim() = getSharedPreferences().getBoolean(KEY_IS_SHOW_USER_ANIM, false);

    /**
     * 获取用户是否是第一次登录
     */
    fun getUserIsFirstLogin() = getSharedPreferences().getBoolean(KEY_IS_FIRST_LOGIN, true)


    private fun getSharedPreferences(): SharedPreferences {
        return AndyApplication.INSTANCE.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}