package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jennifer.andy.simpleeyes.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.base.view.BaseView
import com.jennifer.andy.simpleeyes.utils.getGenericInstance


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<V, T : BasePresenter<V>> : BaseAppCompatFragment(), BaseView {

    protected lateinit var mPresenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getGenericInstance(this, 1)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter.attachView(this as V, this)
        return super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

}