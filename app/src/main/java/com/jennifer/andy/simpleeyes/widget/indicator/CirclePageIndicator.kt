package com.jennifer.andy.simpleeyes.widget.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2018/5/15 11:41
 * Description:圆形指示器
 */

class CirclePageIndicator : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {

    }

}