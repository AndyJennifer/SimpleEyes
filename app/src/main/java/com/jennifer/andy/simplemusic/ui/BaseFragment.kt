package com.jennifer.andy.simplemusic.ui

import android.os.Bundle
import com.jennifer.andy.simplemusic.utils.SystemUtils


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseFragment<T : BasePresenter<*, E>, E : BaseModel> : BaseAppCompatFragment() {

    protected var mPresenter: T? = null
    protected var mModel: E? = null

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
}