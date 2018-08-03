package com.jennifer.andy.simpleeyes.net


/**
 * Author:  andy.xwt
 * Date:    2017/10/15 09:54
 * Description:
 */

object Api {


    /**
     * 主域名
     */
    val BASE_URL: String get() = "http://baobab.kaiyanapp.com/"

    /**
     * 获取默认Service
     */
    fun getDefault() = RetrofitConfig.getDefaultService()


}