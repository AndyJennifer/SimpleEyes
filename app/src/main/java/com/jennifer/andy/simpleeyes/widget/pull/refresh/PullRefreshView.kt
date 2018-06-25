package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Author:  andy.xwt
 * Date:    2018/6/25 17:29
 * Description:
 */

abstract class PullRefreshView : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    /**
     * 处理下拉事件
     */
    abstract fun handlePullEvent(dy: Float)
}