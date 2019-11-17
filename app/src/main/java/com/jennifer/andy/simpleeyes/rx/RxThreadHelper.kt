package com.jennifer.andy.simpleeyes.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Author:  andy.xwt
 * Date:    2017/10/24 10:42
 * Description:网络请求帮助类处理。默认将观察者的方法切换到主线程中运行
 */

object RxThreadHelper {


    /**
     * 将Observable类型的观察者切换到主线程中运行
     */
    fun <T> switchObservableThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { it ->
            it.flatMap { Observable.just(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 将Flowable类型的观察者切换到主线程中运行
     */
    fun <T> switchFlowableThread(): FlowableTransformer<T, T> {
        return FlowableTransformer { it ->
            it.flatMap { Flowable.just(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 将Single类型的观察者切换到主线程中运行
     */
    fun <T> switchSingleThread(): SingleTransformer<T, T> {
        return SingleTransformer { it ->
            it.flatMap { Single.just(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }


    /**
     * 将Maybe类型的观察者切换到主线程中运行
     */
    fun <T> switchMaybeThread(): MaybeTransformer<T, T> {
        return MaybeTransformer { it ->
            it.flatMap { Maybe.just(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}