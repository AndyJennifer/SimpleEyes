package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ProgressBar


/**
 * Author:  andy.xwt
 * Date:    2018/2/27 18:14
 * Description:竖直progressBar
 */

class VerticalProgressBar : ProgressBar {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        canvas.rotate(-90f)//反转90度，将水平ProgressBar竖起来
        canvas.translate(-height.toFloat(), 0f)//将经过旋转后得到的VerticalProgressBar移到正确的位置,注意经旋转后宽高值互换
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(heightMeasureSpec, widthMeasureSpec)//互换宽高值
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldw, oldh)//互换宽高值
    }

}