package com.jennifer.andy.simpleeyes.widget.font

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView


/**
 * Author:  andy.xwt
 * Date:    2017/10/30 16:45
 * Description:自定义字体textView
 */

open class CustomFontTextView : TextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            TypefaceManager.setTextTypeFace(context, attrs, this)
        }
    }

    /**
     * 根据字体类型设置字体
     *
     * @param fontType
     */
    fun setFontType(fontType: FontType) {
        if (!isInEditMode) {
            TypefaceManager.setTextTypeFace(this, fontType)
        }
    }

    /** 根据字体名称设置，如果没有找到相应的字体，则使用默认的字体
     * @param fontName   字体名称
     * @param defaultFontType 默认字体
     */
    fun setFontType(fontName: String?, defaultFontType: FontType = FontType.BOLD) {
        if (!isInEditMode) {
            val fontType = TypefaceManager.getFontTypeByName(fontName)
            TypefaceManager.setTextTypeFace(this, fontType ?: defaultFontType)


        }
    }


}