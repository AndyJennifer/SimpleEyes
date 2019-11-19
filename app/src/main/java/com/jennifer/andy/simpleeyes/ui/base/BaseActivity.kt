package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.utils.getGenericInstance


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:04
 * Description: 基础类activity
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V, T : BasePresenter<V>> : BaseAppCompatActivity(), BaseView {


    protected lateinit var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = getGenericInstance(this, 1)
        lifecycle.addObserver(mPresenter)
        mPresenter.attachView(this as V, this)
        super.onCreate(savedInstanceState)

    }


    override fun showLoading() {
        mMultipleStateView.showLoading()
    }

    override fun showNetError(onClickListener: View.OnClickListener) {
        mMultipleStateView.showNetError(onClickListener)
    }

    override fun showEmpty(onClickListener: View.OnClickListener) {
        mMultipleStateView.showEmpty(onClickListener)
    }

    override fun showContent() {
        mMultipleStateView.showContent()
    }

}