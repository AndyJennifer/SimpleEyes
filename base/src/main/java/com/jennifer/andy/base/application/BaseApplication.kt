package com.jennifer.andy.base.application

import android.app.Application


/**
 * Author:  andy.xwt
 * Date:    2019-12-08 17:38
 * Description: 基础Application
 */

open class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}