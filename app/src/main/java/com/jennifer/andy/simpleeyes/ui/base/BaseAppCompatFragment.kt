package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseAppCompatFragment : SupportFragment() {

    protected lateinit var LOG_TAG: String
    protected lateinit var mMultipleStateView: MultipleStateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOG_TAG = this.javaClass.simpleName
        if (arguments != null) {
            getBundleExtras(arguments!!)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getContentViewLayoutId() != 0) {
            mMultipleStateView = MultipleStateView(context!!)
            return View.inflate(context, getContentViewLayoutId(), mMultipleStateView)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }


    /**
     * 获取bundle中相应data
     */
    open fun getBundleExtras(extras: Bundle) {}

    /**
     * 获取资源id
     */
    abstract fun getContentViewLayoutId(): Int

    /**
     * 初始化view
     */
    open fun initView(savedInstanceState: Bundle?) {

    }
}