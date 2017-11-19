package com.jennifer.andy.simpleeyes.widget.pull

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout


/**
 * Author:  andy.xwt
 * Date:    2017/11/11 16:22
 * Description:
 */

abstract class PullToZoomBase<T : View> : LinearLayout, PullToZoom<T> {

    private var mTouchSlop = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        gravity = Gravity.CENTER
    }

    override fun getZoomView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeaderView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPullRootView(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPullToZoomEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isZooming(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isParallax(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleStyledAttributes(typedArray: TypedArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}