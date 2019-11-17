package com.jennifer.andy.simpleeyes.rx.error

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 21:27
 * Description:该类会获取配置的重试参数。判断当前重试的次数是否大于配置的重试次数，如果比配置的小，那么会判断重试
 * 的条件，如果为true,那么就会重试，反之直接发送Error信息
 */

class ObservableRetryDelay(
        val retryConfigProvider: (Throwable) -> RetryConfig
) : Function<Observable<Throwable>, ObservableSource<*>> {

    private var retryCount: Int = 0

    override fun apply(throwableObs: Observable<Throwable>): ObservableSource<*> {
        return throwableObs
                .flatMap { error ->
                    val (maxRetries, delay, retryCondition) = retryConfigProvider(error)

                    if (++retryCount <= maxRetries) {
                        retryCondition().flatMapObservable { retry ->
                            if (retry)
                                Observable.timer(delay.toLong(), TimeUnit.MILLISECONDS)
                            else
                                Observable.error<Any>(error)
                        }
                    } else Observable.error<Any>(error)
                }
    }
}