package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


/**
 * Author:  andy.xwt
 * Date:    2019-11-05 23:11
 * Description:
 */

abstract class BaseDataBindFragment<T : ViewDataBinding> : Fragment() {

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
        initView(savedInstanceState)
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
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

}