package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView
import com.jennifer.andy.simpleeyes.player.MeasureHelper


/**
 * Author:  andy.xwt
 * Date:    2019-08-27 10:43
 * Description:全屏VideoView
 */

class FullScreenVideoView : VideoView {


    private var mMeasureHelper: MeasureHelper = MeasureHelper(this)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mMeasureHelper.measuredWidth, mMeasureHelper.measuredHeight)
    }

    /**
     * 设置视频比例
     */
    fun setAspectRatio(aspectRatio: Int) {
        mMeasureHelper.setAspectRatio(aspectRatio)
        requestLayout()
    }

}