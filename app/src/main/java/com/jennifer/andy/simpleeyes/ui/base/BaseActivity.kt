package com.jennifer.andy.simplemusic.ui

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simplemusic.R
import com.jennifer.andy.simplemusic.utils.SystemUtils
import com.jennifer.andy.simplemusic.widget.MultipleStateView
import kotterknife.bindView


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:04
 * Description: 基础类activity
 */

abstract class BaseActivity<T : BasePresenter<*, E>, E : BaseModel> : BaseAppCompatActivity(), BaseView {


    protected var mPresenter: T? = null
    protected var mModel: E? = null

    protected val mMultipleStateView by bindView<MultipleStateView>(R.id.multiple_state_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = SystemUtils.getGenericInstance(this, 0)
        mModel = SystemUtils.getGenericInstance(this, 1)
        if (mModel != null) {
            mPresenter?.attachModel(mModel)
        }
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
        if (mPresenter != null) {
            mPresenter?.detach()
        }
    }

}