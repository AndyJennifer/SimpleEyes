package com.jennifer.andy.simpleeyes.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.datasource.UserSettingLocalDataSource


/**
 * Author:  andy.xwt
 * Date:    2019/1/14 19:04
 * Description: 1.登录拦截,判断用户是否登录
 */
@Interceptor(priority = 8, name = "登录拦截器")
class RouteIntercept : IInterceptor {

    companion object {
        const val SHOULD_LOGIN = 1 ushr 30
    }


    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        when (postcard.extra) {
            SHOULD_LOGIN -> judgeUserIsLogin(postcard, callback)
            else -> {
                callback.onContinue(postcard)
            }
        }
    }

    /**
     * 判断用户是否登录，如果没有登录则显示登录界面
     */
    private fun judgeUserIsLogin(postcard: Postcard, callback: InterceptorCallback) {
        val userIsLogin = UserSettingLocalDataSource.isUserLogin
        if (!userIsLogin) //如果用户没登录，直接跳转到登录界面
            ARouter.getInstance().build("/github/Login").navigation()
        else
            callback.onContinue(postcard)

    }

    override fun init(context: Context?) {

    }


}