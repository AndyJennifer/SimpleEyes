package com.jennifer.andy.simplemusic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.jennifer.andy.simplemusic.R


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 17:10
 * Description:
 */
open class BaseStateView : View,View.OnClickListener {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStateView, defStyleAttr, 0)
        a.recycle()
    }

    /**
     * 显示当前布局
     */
    fun show() {

    }

    /**
     * 隐藏当前布局
     */
    fun hide() {

    }

    override fun onClick(p0: View?) {

    }
}
