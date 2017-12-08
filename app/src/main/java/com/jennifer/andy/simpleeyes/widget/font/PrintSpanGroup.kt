package com.jennifer.andy.simpleeyes.widget.font

import android.animation.ObjectAnimator
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.util.Property
import android.widget.TextView


/**
 * Author:  andy.xwt
 * Date:    2017/11/27 15:29
 * Description:
 */

class PrintSpanGroup constructor(printText: CharSequence) {

    private var mSpans: MutableList<PrintSpan> = mutableListOf()
    private var spannableString: SpannableString = SpannableString(printText)
    private var mAlpha: Float = 255f

    private val TYPE_WRITER_GROUP_ALPHA_PROPERTY = object : Property<PrintSpanGroup, Float>(Float::class.java, "type_writer_group_alpha_property") {

        override fun set(printGroup: PrintSpanGroup, alpha: Float) {
            setAlpha(alpha)
        }

        override fun get(printGroup: PrintSpanGroup): Float? {
            return mAlpha
        }
    }

    init {
        buildPrintSpanGroup(0, printText.length - 1)
    }

    /**
     * 将text拆分成单个span
     */
    private fun buildPrintSpanGroup(start: Int, end: Int) {
        for (i in start..end) {
            val printSpan = PrintSpan(Color.TRANSPARENT)
            spannableString.setSpan(printSpan, i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            mSpans.add(printSpan)
        }

    }

    /**
     * 设置单个span的颜色显示
     */
    private fun setAlpha(alpha: Float) {
        val size = mSpans.size
        var total = size * 1f * alpha//计算范围，哪个范围显示，哪个范围不显示
        for (i in 0 until size) {
            val printSpan = mSpans[i]
            if (i <= total) {
                printSpan.printAlpha = 255
            } else {
                printSpan.printAlpha = 0
            }
        }
    }

    /**
     * 执行打印请求
     */
    fun startPrint(textView: TextView) {
        val objectAnimator = ObjectAnimator.ofFloat(this, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0f, 1f)
        objectAnimator.duration = 500
        objectAnimator.start()
        objectAnimator.addUpdateListener {
            textView.text = spannableString
        }

    }


}