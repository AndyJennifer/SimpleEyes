package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.utils.SystemUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simplemusic.widget.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseFragment<T : BasePresenter<*, E>, E : BaseModel> : BaseAppCompatFragment(), BaseView {

    protected var mPresenter: T? = null
    protected var mModel: E? = null
    protected val mMultipleStateView by bindView<MultipleStateView>(R.id.multiple_state_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = SystemUtils.getGenericInstance(this, 0)
        mModel = SystemUtils.getGenericInstance(this, 0)
        if (mModel != null) {
            mPresenter?.attachModel(mModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detach()
        }
    }

    override fun showLoading() {

    }

    override fun showNetError(onClickListener: View.OnClickListener) {

    }

    override fun showEmpty(onClickListener: View.OnClickListener) {

    }
}