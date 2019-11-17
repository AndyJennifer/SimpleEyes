package com.jennifer.andy.simpleeyes.rx.error

import io.reactivex.*


/**
 * Author:  andy.xwt
 * Date:    2019-11-17 21:22
 * Description: RxJava全局错误处理，包括：
 * 1.全局的onNext拦截器[globalOnNextInterceptor]
 * 2.全局的错误重试处理器[globalOnErrorResume]，该属性用于当发生错误时，重新发送新的Observable
 * 3.全局重试配置参数[RetryConfig]，该属性用于配置重试次数以及重试的时间,是否需要重试，需要通过内部的condition进行判断
 * 4.全局的错误处理器[globalDoOnErrorConsumer]，该属性会在观察者的onError方法之前运行
 */

class GlobalErrorTransformer<T> constructor(
        private val globalOnNextInterceptor: (T) -> Observable<T> = { Observable.just(it) },
        private val globalOnErrorResume: (Throwable) -> Observable<T> = { Observable.error(it) },
        private val retryConfigProvider: (Throwable) -> RetryConfig = { RetryConfig() },
        private val globalDoOnErrorConsumer: (Throwable) -> Unit = { }
) : ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer {

    override fun apply(upstream: Observable<T>): Observable<T> =
            upstream
                    .flatMap {
                        globalOnNextInterceptor(it)
                    }
                    .onErrorResumeNext { throwable: Throwable ->
                        globalOnErrorResume(throwable)
                    }
                    .retryWhen(ObservableRetryDelay(retryConfigProvider))
                    .doOnError(globalDoOnErrorConsumer)

    override fun apply(upstream: Completable): Completable =
            upstream
                    .onErrorResumeNext {
                        globalOnErrorResume(it).ignoreElements()
                    }
                    .retryWhen(FlowableRetryDelay(retryConfigProvider))
                    .doOnError(globalDoOnErrorConsumer)

    override fun apply(upstream: Flowable<T>): Flowable<T> =
            upstream
                    .flatMap {
                        globalOnNextInterceptor(it)
                                .toFlowable(BackpressureStrategy.BUFFER)
                    }
                    .onErrorResumeNext { throwable: Throwable ->
                        globalOnErrorResume(throwable)
                                .toFlowable(BackpressureStrategy.BUFFER)
                    }
                    .retryWhen(FlowableRetryDelay(retryConfigProvider))
                    .doOnError(globalDoOnErrorConsumer)

    override fun apply(upstream: Maybe<T>): Maybe<T> =
            upstream
                    .flatMap {
                        globalOnNextInterceptor(it)
                                .firstElement()
                    }
                    .onErrorResumeNext { throwable: Throwable ->
                        globalOnErrorResume(throwable)
                                .firstElement()
                    }
                    .retryWhen(FlowableRetryDelay(retryConfigProvider))
                    .doOnError(globalDoOnErrorConsumer)

    override fun apply(upstream: Single<T>): Single<T> =
            upstream
                    .flatMap {
                        globalOnNextInterceptor(it)
                                .firstOrError()
                    }
                    .onErrorResumeNext { throwable ->
                        globalOnErrorResume(throwable)
                                .firstOrError()
                    }
                    .retryWhen(FlowableRetryDelay(retryConfigProvider))
                    .doOnError(globalDoOnErrorConsumer)

}