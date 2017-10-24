package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.utils.SystemUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:04
 * Description: 基础类activity
 */

abstract class BaseActivity<T : BasePresenter<Any, Any>, E : BaseModel> : BaseAppCompatActivity(), BaseView {


    protected lateinit var mPresenter: T
    protected lateinit var mModel: E

    protected val mMultipleStateView by bindView<MultipleStateView>(R.id.multiple_state_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = SystemUtils.getGenericInstance(this, 0)
        mModel = SystemUtils.getGenericInstance(this, 1)
        mPresenter.attachModel(mModel)
        mPresenter.attachView(this)

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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()

    }

}