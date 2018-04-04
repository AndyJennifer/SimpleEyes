package com.jennifer.andy.simpleeyes.widget.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan


/**
 * Author:  andy.xwt
 * Date:    2018/4/4 15:56
 * Description:文字图片居中显示
 */

class CenterAlignImageSpan : ImageSpan {

    constructor(drawable: Drawable) : super(drawable)

    constructor(bitmap: Bitmap) : super(bitmap)

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val drawable = drawable
        val fm = paint.fontMetrics
        val transY = (y + fm.descent + y + fm.ascent) / 2 - drawable.bounds.bottom / 2//计算y方向的位移
        canvas.save()
        canvas.translate(x, transY)//绘制图片位移一段距离
        drawable.draw(canvas)
        canvas.restore()
    }


}
