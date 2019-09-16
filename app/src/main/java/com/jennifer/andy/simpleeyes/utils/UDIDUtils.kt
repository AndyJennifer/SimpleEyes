package com.jennifer.andy.simpleeyes.utils

import java.util.*

/**
 * 获取随机uuid
 */
fun getRandomUUID() = UUID.randomUUID().toString().replace("-", "")
