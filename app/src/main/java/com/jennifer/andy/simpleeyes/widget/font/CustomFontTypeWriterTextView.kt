package com.jennifer.andy.simpleeyes.widget.font

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet


/**
 * Author:  andy.xwt
 * Date:    2017/12/12 11:10
 * Description:打印文字TextView
 */

class CustomFontTypeWriterTextView : CustomFontTextView {


    private lateinit var mSloganSpanGroup: PrintSpanGroup

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 打印文字
     */
    fun printText(text: String?, printTime: Long = 0) {
        if (!TextUtils.isEmpty(text)) {
            mSloganSpanGroup = PrintSpanGroup(text!!, printTime)
            mSloganSpanGroup.startPrint(this)
        }
    }


}