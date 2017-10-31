package com.jennifer.andy.simpleeyes.widget.font

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView


/**
 * Author:  andy.xwt
 * Date:    2017/10/30 16:45
 * Description:
 */

class CustomFontTextView : TextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        if (!isInEditMode) {

        }
    }

}