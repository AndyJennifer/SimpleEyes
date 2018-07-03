package com.jennifer.andy.simpleeyes.rx

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Author:  andy.xwt
 * Date:    2017/10/24 10:42
 * Description:网络请求帮助类处理。对请求结果进行了判断，对线程进行了处理
 */

object RxHelper {


    /**
     * 处理结果数据
     */
    fun <T> handleResult(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.flatMap {
                if (it != null) {
                    createSuccessData(it)
                } else {
                    Observable.error(RxServerException("服务器异常"))
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 创建成功返回的数据
     */
    private fun <T> createSuccessData(t: T): Observable<T> {
        return Observable.create {
            try {
                it.onNext(t)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

}