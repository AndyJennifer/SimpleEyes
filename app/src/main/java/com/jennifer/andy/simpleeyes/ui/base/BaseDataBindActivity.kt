package com.jennifer.andy.simpleeyes.ui.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * Author:  andy.xwt
 * Date:    2019-11-04 23:40
 * Description:实现了DataBind 的Activity
 */

abstract class BaseDataBindActivity<T : ViewDataBinding> : AppCompatActivity() {


    /**
     * 上下文对象
     */
    protected lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras(intent.extras)
        if (getContentViewLayoutId() != 0) {
            DataBindingUtil.setContentView<T>(this, getContentViewLayoutId())
            initView(savedInstanceState)
        } else
            throw  IllegalArgumentException("You must set layout id")
    }

    /**
     * 初始化数据
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     *  获取bundle 中的数据
     */
    open fun getBundleExtras(extras: Bundle?) {}

    /**
     * 获取当前布局id
     */
    abstract fun getContentViewLayoutId(): Int
}