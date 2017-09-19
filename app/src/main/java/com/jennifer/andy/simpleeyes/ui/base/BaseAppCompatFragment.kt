package com.jennifer.andy.simplemusic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseAppCompatFragment : SupportFragment() {

    protected var LOG_TAG: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOG_TAG = this.javaClass.simpleName
        if (arguments != null) {
            getBundleExtras(arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getContentViewLayoutId() != 0) {
            inflater?.inflate(getContentViewLayoutId(), null)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }


    /**
     * 获取bundle中相应data
     */
    abstract fun getBundleExtras(extras: Bundle)

    /**
     * 获取资源id
     */
    abstract fun getContentViewLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)
}