package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseFragment<V, T : BasePresenter<V>> : BaseAppCompatFragment(), BaseView {

    protected lateinit var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter.attachView(this as V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
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

    /**
     * 初始化Presenter
     */
    abstract fun initPresenter(): T

}