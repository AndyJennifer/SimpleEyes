package com.jennifer.andy.simpleeyes.base.data

import android.content.Context
import com.jennifer.andy.base.application.BaseApplication
import com.jennifer.andy.base.data.ILocalDataSource
import com.jennifer.andy.base.ext.boolean


/**
 * Author:  andy.xwt
 * Date:    2019-11-29 16:57
 * Description:用户配置信息,关于高级函数与委托可以参看PreferencesUtils.kt文件
 */

object UserSettingLocalDataSource : ILocalDataSource {


    private const val NAME = "andy."
    private const val KEY_IS_USER_LOGIN = "is_user_login"
    private const val KEY_IS_FIRST_LOGIN = "is_first_login"
    private const val KEY_IS_SHOW_USER_ANIM = "key_is_show_user_anim"


    private fun getSharedPreferences() = BaseApplication.INSTANCE.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    /**
     * 是否登录
     */
    var isUserLogin by getSharedPreferences().boolean(KEY_IS_USER_LOGIN)

    /**
     * 是否是第一次登录
     */
    var isFirstLogin by getSharedPreferences().boolean(KEY_IS_FIRST_LOGIN, true)


    /**
     * 是否显示登录动画
     */
    var isShowUserAnim by getSharedPreferences().boolean(KEY_IS_SHOW_USER_ANIM)


}