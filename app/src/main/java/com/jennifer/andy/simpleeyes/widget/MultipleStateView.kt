package com.jennifer.andy.simplemusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.jennifer.andy.simplemusic.R


/**
 * Author:  andy.xwt
 * Date:    2017/8/31 22:40
 * Description:多状态布局。包含加载界面，错误界面，网络异常界面，内容界面，空界面
 */

class MultipleStateView : RelativeLayout {


    private var mEmptyView: View? = null
    private var mNetErrorView: View? = null
    private var mLoadingView: View? = null
    private var mContentViews: MutableList<View> = mutableListOf()

    enum class State {
        EMPTY, NET_ERROR, LOADING, CONTENT
    }


    private var mEmptyViewResId: Int

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStateView, defStyleAttr, 0)
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStateView_empty_view, 0)
        a.recycle()
    }

    override fun addView(child: View?) {
        super.addView(child)
        child?.let { mContentViews.add(it) }
    }

    fun showLoading() {
        switchContent(State.LOADING)
    }

    fun showContent() {
        switchContent(State.CONTENT)
    }

    fun showEmpty() {
        switchContent(State.EMPTY)
    }

    fun showNetError() {
        switchContent(State.NET_ERROR)
    }

    /**
     * 设置空数据view
     */
    private fun setEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
        } else {
            mEmptyView!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示空数据View
     */
    private fun showEmptyView() {
        setEmptyView()
        hideLoadingView()
        hideNetErrorView()
        setContentViewVisible(false)
    }


    /**
     * 隐藏空数据view
     */
    private fun hideEmptyView() {
        mEmptyView?.let { it.visibility = View.GONE }
    }


    /**
     * 设置网络异常view
     */
    private fun setNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(context).inflate(R.layout.layout_net_error_view, this, true)
        } else {
            mNetErrorView!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示网络异常view
     */
    private fun showNetErrorView() {
        setNetErrorView()
        hideLoadingView()
        hideEmptyView()
        setContentViewVisible(false)
    }

    /**
     * 隐藏网络异常view
     */
    private fun hideNetErrorView() {
        mLoadingView?.let { it.visibility = View.GONE }
    }

    /**
     * 设置加载中view
     */
    private fun setLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, this, true)
        } else {
            mLoadingView!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示加载view
     */
    private fun showLoadingView() {
        setLoadingView()
        hideEmptyView()
        hideNetErrorView()
        setContentViewVisible(false)
    }

    /**
     * 隐藏加载view
     */
    private fun hideLoadingView() {
        mLoadingView?.let { it.visibility = View.GONE }
    }


    /**
     * 设置内容界面是否显示
     */
    private fun setContentViewVisible(isVisible: Boolean) {
        for (mContentView in mContentViews) {
            mContentView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

    }

    /**
     * 显示内容视图
     */
    private fun showContentView() {
        hideEmptyView()
        hideNetErrorView()
        hideLoadingView()
        setContentViewVisible(true)
    }

    /**
     * 切换布局
     */
    private fun switchContent(state: State) {
        when (state) {
            State.EMPTY -> showEmptyView()
            State.LOADING -> showLoadingView()
            State.NET_ERROR -> showNetErrorView()
            State.CONTENT -> showContentView()
        }
    }


}