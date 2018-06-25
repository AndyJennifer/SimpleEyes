package com.jennifer.andy.simpleeyes.widget.pull.head

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.widget.pull.refresh.PullRefreshView


/**
 * Author:  andy.xwt
 * Date:    2018/6/25 13:16
 * Description:
 */

class EliteHeaderView : PullRefreshView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.refresh_daily_elite_header, this, true)
    }

    override fun handlePullEvent(dy: Float) {
        //处理其中的
    }
}