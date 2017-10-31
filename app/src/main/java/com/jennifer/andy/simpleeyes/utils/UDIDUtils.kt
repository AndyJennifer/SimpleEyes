package com.jennifer.andy.simpleeyes.utils

import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/10/31 15:41
 * Description: uuid生成工具类
 */

object UDIDUtils {

    /**
     * 获取随机uuid
     */
    fun getRandomUUID() = UUID.randomUUID().toString().replace("-", "")
}