package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jennifer.andy.base.ui.LazyFragment


/**
 * Author:  andy.xwt
 * Date:    2019-11-05 23:11
 * Description:
 */

abstract class BaseDataBindFragment<T : ViewDataBinding> : LazyFragment() {

    protected lateinit var mDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (getContentViewLayoutId() != 0) {
            mDataBinding = DataBindingUtil.inflate(inflater, getContentViewLayoutId(), container, false)
            mDataBinding.lifecycleOwner = viewLifecycleOwner
            return mDataBinding.root
        } else throw  IllegalArgumentException("You must set layout id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewOnCreated(savedInstanceState)
    }


    /**
     * 获取bundle中相应data
     */
    open fun getBundleExtras(extras: Bundle?) {}

    /**
     * 获取资源id
     */
    abstract fun getContentViewLayoutId(): Int

    /**
     * 在调用[onViewCreated]时调用的初始化方法，如果需要懒加载可以调用[LazyFragment.lazyInit]
     */
    abstract fun initViewOnCreated(savedInstanceState: Bundle?)


}