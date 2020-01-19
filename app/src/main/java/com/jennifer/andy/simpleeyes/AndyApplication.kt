package com.jennifer.andy.simpleeyes

import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.jennifer.andy.base.application.BaseApplication
import com.jennifer.andy.simpleeyes.ui.author.di.authorModel
import com.jennifer.andy.simpleeyes.ui.feed.di.feedModel
import com.jennifer.andy.simpleeyes.ui.follow.di.followModule
import com.jennifer.andy.simpleeyes.ui.home.di.homeModule
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:27
 * Description:
 */

class AndyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initARoute()
        initFresco()
        initLeakCanary()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AndyApplication)
            modules(listOf(
                    homeModule,
                    feedModel,
                    authorModel,
                    followModule))
        }
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