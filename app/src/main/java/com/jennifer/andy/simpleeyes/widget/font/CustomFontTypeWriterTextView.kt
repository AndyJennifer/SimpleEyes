package com.jennifer.andy.simpleeyes.widget.font

import android.content.Context
import android.util.AttributeSet


/**
 * Author:  andy.xwt
 * Date:    2017/11/27 14:24
 * Description: 自定义打字效果TextView
 */

class CustomFontTypeWriterTextView : CustomFontTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun setText(text: CharSequence, type: BufferType?) {
        val printSpanGroup = PrintSpanGroup(text, this)
        printSpanGroup.startPrint()
    }



}