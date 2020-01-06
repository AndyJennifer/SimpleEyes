package com.jennifer.andy.base.rx.error

import com.jennifer.andy.base.utils.toast
import retrofit2.HttpException


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 22:04
 * Description: 全部的错误处理，当出现错误时，弹出相应的toast
 */

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
        globalDoOnErrorConsumer = { error ->
            when (error) {
                is HttpException -> {
                    when (error.code()) {
                        401 -> toast { "401 Unauthorized" }
                        404 -> toast { "404 failure" }
                        500 -> toast { "500 server failure" }
                        else -> toast { "network failure" }
                    }
                }
                else -> {
                    toast { error.message.toString() }
                }
            }
        }
)