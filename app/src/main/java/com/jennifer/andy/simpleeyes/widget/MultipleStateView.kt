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
    private var mContentView: View? = null

    enum class State {
        EMPTY, NET_ERROR, LOADING
    }


    private var mEmptyViewResId: Int

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStateView, defStyleAttr, 0)
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStateView_empty_view, 0)
        a.recycle()
    }

    /**
     * 设置加载中view
     */
    fun setLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, null)
            addView(mLoadingView)

        } else {
            mLoadingView!!.visibility = View.VISIBLE
        }
        switchContent(State.LOADING)

    }

    /**
     * 设置网络异常view
     */
    fun setNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(context).inflate(R.layout.layout_net_error_view, null)
            addView(mNetErrorView)
        } else {
            mNetErrorView!!.visibility = View.VISIBLE
        }
        switchContent(State.NET_ERROR)
    }

    /**
     * 设置空数据view
     */
    fun setEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null)
            addView(mEmptyView)
        } else {
            mEmptyView!!.visibility = View.VISIBLE
        }
        switchContent(State.EMPTY)
    }


    /**
     * 切换布局
     */
    fun switchContent(state: State) {
        when (state) {

        }

    }


}