package com.jennifer.andy.simpleeyes.manager

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Author:  andy.xwt
 * Date:    2017/9/6 19:00
 * Description:用于管理单个presenter生命周期中RxJava相关代码的生命周期处理
 */

class RxManager {

    /**
     * 管理Observables和Subscribers订阅
     * 注意一旦取消订阅，重新创建对象
     */
    private var mSerialDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * 添加请求管理
     */
    fun add(disposable: Disposable) {
        mSerialDisposable.add(disposable)
    }

    /**
     * 单个presenter生命周期结束，取消订阅
     */
    fun clear() {
        mSerialDisposable.dispose()
        mSerialDisposable = CompositeDisposable()
    }
}