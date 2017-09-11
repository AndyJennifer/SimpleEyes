package com.jennifer.andy.simplemusic.widget

import android.content.Context
import android.util.AttributeSet
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
    private var mErrorView: View? = null
    private var mNetErrorView: View? = null
    private var mLoaddingView: View? = null
    private var mContentView: View? = null

    private var mEmptyViewResId: Int

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStateView, defStyleAttr, 0)
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStateView_empty_view, 0)
        a.recycle()
    }

    //todo 可以自定义布局，可以include,可以设置默认布局，可以设置进入动画



}