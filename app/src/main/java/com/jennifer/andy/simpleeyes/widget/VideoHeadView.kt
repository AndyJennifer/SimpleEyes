package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/12/28 17:12
 * Description:视频信息，头部界面
 */

class VideoHeadView : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_video_head_view, this, true)

    }

}