package com.jennifer.andy.simpleeyes.update

import android.app.Application
import com.jennifer.andy.simpleeyes.config.GlobalConfig
import com.jennifer.andy.simpleeyes.utils.ProcessUtils
import java.lang.reflect.ParameterizedType


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 11:29
 * Description:
 */

abstract class UpdateApplication<T : LocalUpdateService> : Application() {


    private lateinit var mUpdateParams: LocalUpdateService.UpdateParams

    override fun onCreate() {
        super.onCreate()
        GlobalConfig.setApplicationContext(applicationContext)
        UpdateHelper.setUpdateService((javaClass.genericSuperclass as ParameterizedType) as Class<T>)
        val currentProcessName = ProcessUtils.getProcessName(applicationContext)
        val currentPageName = applicationContext.packageName
        if (currentPageName == currentProcessName) {
            mUpdateParams = initUpdateParams()
            if (mUpdateParams.checkUpdateProtocol == null || (mUpdateParams.checkUpdateProtocol?.isValid()!!)) {
                throw  IllegalArgumentException("invalid parameters!")
            } else {
                UpdateHelper.startLocalUpdateService(applicationContext, mUpdateParams)
            }
        }

    }

    /**
     * 初始化更新参数
     */
    abstract fun initUpdateParams(): LocalUpdateService.UpdateParams

}