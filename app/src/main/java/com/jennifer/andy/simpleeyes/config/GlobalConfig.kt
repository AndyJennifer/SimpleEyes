package com.jennifer.andy.simpleeyes.config

import android.content.Context


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 11:50
 * Description: 全局配置
 */

class GlobalConfig {

    companion object {

        private val ASSETS_CHANNEL_FILE_NAME = "channel.mf"

        private val DEFAULT_CHANNEL = "simpleeyes_debug"

        private val INTERNAL_PACKAGE_CHANNEL = "simpleeyes_internal"

        private var debug = false

        private var appContext: Context? = null

        private var rooDir = ""

        private var sLastChannel: String? = null


        fun setApplicationContext(context: Context) {
            appContext = context
        }

        fun getApplicationContext() = appContext

        fun setApplicationRootDir(dir: String) {
            rooDir = dir
        }

        fun getAppllicationRootDir() = rooDir

        fun setAppDebug(isDebug: Boolean) {
            debug = isDebug
        }

        fun getIsAppDebug() = debug


        //todo 这里lastChannel 是否是上次浏览的位置呢？ 以后来写

    }
}