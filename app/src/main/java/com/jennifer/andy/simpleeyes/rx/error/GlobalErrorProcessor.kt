package com.jennifer.andy.simpleeyes.rx.error

import com.jennifer.andy.simpleeyes.utils.toast
import retrofit2.HttpException


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 22:04
 * Description:
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