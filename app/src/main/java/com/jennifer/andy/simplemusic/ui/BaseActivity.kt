package com.jennifer.andy.simplemusic.ui

import android.os.Bundle
import com.jennifer.andy.simplemusic.utils.SystemUtils


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:04
 * Description: 基础类activity
 */

abstract class BaseActivity<T : BasePresenter<*, *>, E : BaseModel> : BaseAppCompatActivity() {


    protected var mPresenter: T? = null
    protected var mModel: E? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = SystemUtils.getGenericInstance(this, 0)
        mModel = SystemUtils.getGenericInstance(this, 1)

    }

    /**
     *  初始化view
     */
    fun initView(savedInstanceState: Bundle?) {}


}