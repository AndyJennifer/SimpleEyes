package com.jennifer.andy.simpleeyes

import android.app.Application
import android.content.res.Resources
import android.os.Build
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.jennifer.andy.simpleeyes.config.GlobalConfig
import com.jennifer.andy.simpleeyes.update.CheckUpdateProtocol
import com.jennifer.andy.simpleeyes.update.LocalUpdateService
import com.jennifer.andy.simpleeyes.update.UpdateApplication
import com.jennifer.andy.simpleeyes.utils.AppUtils
import com.jennifer.andy.simpleeyes.utils.UDIDUtils
import com.squareup.leakcanary.LeakCanary
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:27
 * Description:
 */

class AndyApplication : UpdateApplication<LocalUpdateService>() {

    companion object {
        lateinit var INSTANCE: Application
        /**
         * 获取资源文件访问对象
         */
        @JvmStatic
        fun getResource(): Resources {
            return INSTANCE.resources
        }

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        GlobalConfig.setApplicationContext(this)
        GlobalConfig.setAppDebug(false)
        GlobalConfig.setApplicationRootDir("simpleeyes")
        initARoute()
        initFresco()
        initLeakCanary()

        //todo 这里还要做崩溃检查 腾讯的bugly 热更新等操作


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

    override fun initUpdateParams(): LocalUpdateService.UpdateParams {
        val localCheckUpdateProtocol = CheckUpdateProtocol()
        localCheckUpdateProtocol.packageName = applicationContext.packageName
        localCheckUpdateProtocol.versionName = AppUtils.getAppVersionName(applicationContext)
        localCheckUpdateProtocol.versionCode = AppUtils.getAppVersionCode(applicationContext)
        localCheckUpdateProtocol.udid = UDIDUtils.getRandomUUID()
        val localDefault = Locale.getDefault()
        localCheckUpdateProtocol.language = localDefault.displayCountry + "-" + localDefault.displayLanguage
        localCheckUpdateProtocol.rom = Build.MODEL//版本信息
        localCheckUpdateProtocol.romVersion = Build.VERSION.RELEASE
        localCheckUpdateProtocol.appName = "simpleeyes"
        localCheckUpdateProtocol.isOem = false
        val updateParams = LocalUpdateService.UpdateParams()
        updateParams.checkUpdateProtocol = localCheckUpdateProtocol
        return updateParams
    }


}