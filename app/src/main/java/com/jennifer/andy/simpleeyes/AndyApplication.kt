package com.jennifer.andy.simpleeyes

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import com.jennifer.andy.simpleeyes.config.GlobalConfig
import com.jennifer.andy.simpleeyes.update.CheckUpdateProtocol
import com.jennifer.andy.simpleeyes.update.LocalUpdateService
import com.jennifer.andy.simpleeyes.update.UpdateApplication
import com.jennifer.andy.simpleeyes.utils.AppUtils
import com.jennifer.andy.simpleeyes.utils.UDIDUtils
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:27
 * Description:
 */

class AndyApplication : UpdateApplication<LocalUpdateService>() {

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
        GlobalConfig.setApplicationContext(this)
        GlobalConfig.setAppDebug(false)
        GlobalConfig.setApplicationRootDir("simpleeyes")
        //todo ijk player 的初始化
        mApplication = this
        super.onCreate()
        //todo 这里还要做崩溃检查

    }

    override fun initUpdateParams(): LocalUpdateService.UpdateParams {
        var localCheckUpdateProtocol = CheckUpdateProtocol()
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