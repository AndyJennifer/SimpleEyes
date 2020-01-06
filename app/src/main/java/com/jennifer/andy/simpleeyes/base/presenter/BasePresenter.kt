package com.jennifer.andy.simpleeyes.base.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:10
 * Description:
 */

open class BasePresenter<V> : LifecycleObserver {

    protected var mView: V? = null
    protected lateinit var mScopeProvider: AndroidLifecycleScopeProvider

    /**
     * 与view进行关联
     */
    fun attachView(view: V, lifecycleOwner: LifecycleOwner) {
        this.mView = view
        this.mScopeProvider = AndroidLifecycleScopeProvider.from(lifecycleOwner)
    }


}