package com.jennifer.andy.simpleeyes.ui.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2019-12-26 13:34
 * Description: 带多状态的Fragment
 */


abstract class BaseStateViewActivity<T : ViewDataBinding> : BaseDataBindActivity<T>() {


    fun showLoading() {
        getMultipleStateView().showLoading()
    }

    fun showNetError(onClickListener: () -> Unit = {}) {
        getMultipleStateView().showNetError(View.OnClickListener { onClickListener() })
    }

    fun showEmpty(onClickListener: () -> Unit = {}) {
        getMultipleStateView().showEmpty(View.OnClickListener { onClickListener() })
    }

    fun showContent() {
        getMultipleStateView().showContent()
    }


    /**
     * 获取多状态布局
     */
    abstract fun getMultipleStateView(): MultipleStateView
}