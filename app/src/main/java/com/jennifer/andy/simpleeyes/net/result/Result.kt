package com.jennifer.andy.simpleeyes.net.result


/**
 * Author:  andy.xwt
 * Date:    2019-11-29 14:29
 * Description:使用Result来封装加载状态与数据
 * 如果对sealed关键字不熟悉，可以查看https://www.kotlincn.net/docs/reference/sealed-classes.html
 */
sealed class Result<out T> {

    companion object {
        fun <T> success(result: T): Result<T> = Success(result)
        fun <T> idle(): Result<T> = Idle
        fun <T> error(throwable: Throwable): Result<T> = Error(throwable)
    }

    object Idle : Result<Nothing>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    data class Success<out T>(val data: T?) : Result<T>()
}