package com.jennifer.andy.simpleeyes.widget.font

import android.text.TextPaint
import android.text.style.MetricAffectingSpan


/**
 * Author:  andy.xwt
 * Date:    2017/11/27 15:17
 * Description:
 */

class PrintSpan(var printAlpha: Int) : MetricAffectingSpan() {


    override fun updateMeasureState(tp: TextPaint) {
        tp.alpha = printAlpha
    }

    override fun updateDrawState(tp: TextPaint) {
        tp.alpha = printAlpha

    }
}
