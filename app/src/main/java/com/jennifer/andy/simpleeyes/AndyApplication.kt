package com.jennifer.andy.simpleeyes

import android.app.Application
import android.content.Context
import android.content.res.Resources


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:27
 * Description:
 */

class AndyApplication : Application() {


    companion object {
        lateinit var mApplication: Application
        /**
         * 获取当前应用上下文对象
         */
        fun getAppContext(): Context {
            return mApplication
        }

        /**
         * 获取资源文件访问对象
         */
        fun getResource(): Resources {
            return mApplication.resources
        }

    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }


}