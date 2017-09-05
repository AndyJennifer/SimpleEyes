package com.jennifer.andy.simplemusic.ui

import android.content.Context


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:10
 * Description:
 */

class BasePresenter<V, E> {

    protected var mView: V? = null
    protected var mModel: E? = null
    protected var mContext: Context? = null

    /**
     * 与view进行关联
     */
    protected fun attachView(view: V) {
        this.mView = view
        if (view is BaseActivity<*, *>) {
            this.mContext = view
        }
        if (view is BaseFragment) {
            this.mContext = view.activity
        }
    }


    protected fun attachModel(model: E) {
        this.mModel = model
    }

    protected fun detach() {
        mView = null
    }


    /**
     * 判断当前View是否存活
     */
    fun isViewActive() = mView != null


}