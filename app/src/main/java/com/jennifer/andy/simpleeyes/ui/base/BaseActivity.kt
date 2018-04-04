package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:04
 * Description: 基础类activity
 */

abstract class BaseActivity<V, T : BasePresenter<V>> : BaseAppCompatActivity(), BaseView {


    protected lateinit var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter.attachView(this as V)
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()

    }

    /**
     * 初始化Presenter
     */
    abstract fun initPresenter(): T
}