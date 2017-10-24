package com.jennifer.andy.simpleeyes.ui.base.presenter

import android.content.Context
import com.jennifer.andy.simpleeyes.manager.RxManager
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:10
 * Description:
 */

open class BasePresenter<V, E> {

    protected var mView: V? = null
    protected var mModel: E? = null
    protected var mContext: Context? = null

    protected val mRxManager: RxManager = RxManager()

    /**
     * 与view进行关联
     */
    open fun attachView(view: V) {
        this.mView = view
        if (view is BaseActivity<*, *>) {
            this.mContext = view
        }
        if (view is BaseFragment<*, *>) {
            this.mContext = view.getActivity()
        }
    }

    /**
     * 与model进行关联
     */
    open fun attachModel(model: E?) {
        this.mModel = model
    }

    /**
     * 与view解除关联，并取消订阅
     */
    open fun detach() {
        mView = null
        mRxManager.clear()
    }


    /**
     * 判断当前View是否存活
     */
    open fun isViewActive() = mView != null


}