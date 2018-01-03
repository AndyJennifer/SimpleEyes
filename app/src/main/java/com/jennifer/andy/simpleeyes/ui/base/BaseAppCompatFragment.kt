package com.jennifer.andy.simpleeyes.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jennifer.andy.simpleeyes.rx.RxManager
import com.jennifer.andy.simpleeyes.widget.MultipleStateView
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:06
 * Description:
 */

abstract class BaseAppCompatFragment : SupportFragment() {

    protected lateinit var LOG_TAG: String
    protected lateinit var mRxManager: RxManager//之所以有这个管理类，是因为不实现presenter的类也可以进行请求的管理
    protected lateinit var mMultipleStateView: MultipleStateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LOG_TAG = this.javaClass.simpleName
        if (arguments != null) {
            getBundleExtras(arguments)
        }
        mRxManager = RxManager()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getContentViewLayoutId() != 0) {
            mMultipleStateView = MultipleStateView(context)
            return View.inflate(context, getContentViewLayoutId(), mMultipleStateView)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    /**
     * 跳转到相应的activity 并携带bundle数据
     */
    protected fun readyGo(clazz: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(activity, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转到相应的activity,并携带bundle数据，接收返回码
     */
    protected fun readyGoForResult(clazz: Class<*>, bundle: Bundle? = null, requestCode: Int) {
        val intent = Intent(activity, clazz)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
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