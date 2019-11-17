package com.jennifer.andy.simpleeyes.rx.error

import io.reactivex.Single


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 21:12
 * Description: 重试配置，默认情况下不会进行重试，如果需要重试配置，需要重写condition
 */

private const val DEFAULT_RETRY_TIMES = 1 //默认重试次数 1次
private const val DEFAULT_DELAY_DURATION = 1000 //默认推迟时间为1秒

data class RetryConfig(val maxRetries: Int = DEFAULT_RETRY_TIMES,
                       val delay: Int = DEFAULT_DELAY_DURATION,
                       val condition: () -> Single<Boolean> = { Single.just(false) })
