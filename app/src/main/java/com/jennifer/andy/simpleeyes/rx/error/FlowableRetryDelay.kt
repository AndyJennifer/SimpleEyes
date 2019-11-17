package com.jennifer.andy.simpleeyes.rx.error

import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 21:24
 * Description:该类逻辑与[ObservableRetryDelay]一样
 */

class FlowableRetryDelay(
        val retryConfigProvider: (Throwable) -> RetryConfig
) : Function<Flowable<Throwable>, Publisher<*>> {

    private var retryCount: Int = 0

    override fun apply(throwableFlowable: Flowable<Throwable>): Publisher<*> {
        return throwableFlowable
                .flatMap { error ->
                    val (maxRetries, delay, retryTransform) = retryConfigProvider(error)

                    if (++retryCount <= maxRetries) {
                        retryTransform()
                                .flatMapPublisher { retry ->
                                    if (retry)
                                        Flowable.timer(delay.toLong(), TimeUnit.MILLISECONDS)
                                    else
                                        Flowable.error<Any>(error)
                                }
                    } else Flowable.error<Any>(error)
                }
    }
}
