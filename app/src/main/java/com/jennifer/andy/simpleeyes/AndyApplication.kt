package com.jennifer.andy.simpleeyes

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.squareup.leakcanary.LeakCanary


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:27
 * Description:
 */

class AndyApplication : Application() {

    companion object {

        lateinit var INSTANCE: Application

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initARoute()
        initFresco()
        initLeakCanary()
    }

    /**
     * 初始化路由操作
     */
    private fun initARoute() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * 初始化Fresco,打开压缩
     */
    private fun initFresco() {
        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)
    }

    /**
     * 初始化LeakCanary，检测内存泄露
     */
    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }


}