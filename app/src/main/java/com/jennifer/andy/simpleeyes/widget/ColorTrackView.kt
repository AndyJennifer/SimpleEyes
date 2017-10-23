package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.kotlin.DensityUtils


/**
 * Author:  andy.xwt
 * Date:    2017/10/23 12:05
 * Description:轨迹view
 */

class ColorTrackView : View {

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mText: String
    var mTextSize = DensityUtils.sp2px(context, 14f)
    var mTextOriginalColor = 0xFF0000
    var mTextChangeColor = 0xFFFF00

    var mProgress: Float
        set(value) {
            field = value
            invalidate()
        }

    var mDirection: Int

    val DIRECTION_LEFT = 0
    val DIRECTION_RIGHT = 1

    var mTextWidth = 0f
    var mTextHeight = 0f

    private var mTextBound = Rect()
    private var mStartX = 0f
    private var mStartY = 0f


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackView)
        mText = typeArray.getString(R.styleable.ColorTrackView_text)
        mTextSize = typeArray.getDimensionPixelSize(R.styleable.ColorTrackView_text_size, mTextSize)
        mTextOriginalColor = typeArray.getColor(R.styleable.ColorTrackView_text_origin_color, mTextOriginalColor)
        mTextChangeColor = typeArray.getColor(R.styleable.ColorTrackView_text_change_color, mTextChangeColor)
        mProgress = typeArray.getFloat(R.styleable.ColorTrackView_progress, 0f)
        mDirection = typeArray.getInt(R.styleable.ColorTrackView_direction, 0)
        typeArray.recycle()
        mPaint.textSize = mTextSize.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureText()
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
        mStartX = measuredWidth / 2 - mTextWidth / 2
        mStartY = measuredHeight / 2 - mTextHeight / 2
    }

    private fun measureText() {
        mTextWidth = mPaint.measureText(mText)
        mPaint.getTextBounds(mText, 0, mText.length, mTextBound)
        mTextHeight = mTextBound.height().toFloat()

    }

    /**
     * 测量宽度
     */
    private fun measureWidth(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED ->
                result = (mTextWidth + paddingLeft + paddingRight).toInt()
        }
        result = if (mode == MeasureSpec.AT_MOST) {
            Math.min(size, result)
        } else {
            result
        }
        return result
    }

    /**
     * 测量高度
     */
    private fun measureHeight(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        var size = MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED ->
                result = (mTextHeight + paddingTop + paddingBottom).toInt()
        }
        result = if (mode == MeasureSpec.AT_MOST) {
            Math.min(result, size)
        } else {
            result
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val endX = mProgress * mTextWidth + mStartX
        if (mDirection == DIRECTION_LEFT) {
            drawChangeLeft(canvas, mStartX, endX)
            drawOriginalLeft(canvas, mStartX + endX, mStartX + mTextWidth)
        }
    }

    private fun drawChangeLeft(canvas: Canvas, startX: Float, endX: Float) {
        drawTextH(canvas, mTextChangeColor, startX, endX)
    }

    private fun drawOriginalLeft(canvas: Canvas, startX: Float, endX: Float) {
        drawTextH(canvas, mTextOriginalColor, startX, endX)
    }

    private fun drawTextH(canvas: Canvas, color: Int, startX: Float, endX: Float) {
        mPaint.color = color
        canvas.save()
        canvas.clipRect(startX, 0f, endX, measuredHeight.toFloat())
        val fontMetrics = mPaint.fontMetrics
        val baseLine = measuredHeight / 2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        canvas.drawText(mText, mStartX, baseLine, mPaint)
        canvas.restore()

    }
}
