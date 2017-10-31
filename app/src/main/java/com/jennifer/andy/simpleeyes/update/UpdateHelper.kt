package com.jennifer.andy.simpleeyes.update

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.jennifer.andy.simpleeyes.net.Extras


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 14:07
 * Description:更新帮助类
 */

object UpdateHelper {

    private var mUpdateService: Class<out LocalUpdateService>? = null


    /**
     * 设置更新服务
     * @param serviceClass 服务class
     */
    fun setUpdateService(serviceClass: Class<out LocalUpdateService>) {
        mUpdateService = serviceClass
    }

    /**
     * 开启本地更新服务
     * @param context 上下文
     * @param updateParams 更新参数
     * @return 组件信息
     */
    fun startLocalUpdateService(context: Context, updateParams: LocalUpdateService.UpdateParams): ComponentName? {
        val localIntent = Intent(context, mUpdateService)
        localIntent.putExtra(Extras.UPDATE_PARAMS, updateParams)
        return context.startService(localIntent)
    }
}