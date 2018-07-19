package com.jennifer.andy.simpleeyes.widget.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.jennifer.andy.simpleeyes.AndyApplication
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/10/30 17:19
 * Description:字体管理工具类
 */

object TypefaceManager {

    private val mTypeFaceMap: MutableMap<FontType, Typeface> = mutableMapOf()
    private var mTypeFaceIndex: Int = FontType.NORMAL.index

    /**
     * 设置textView字体,如果参数中有字体，就采用本身的，如果没有就根据设置的值设置字体
     *
     *@param context  上下文
     *@param attributes 参数
     *@param textView  textView
     */
    fun setTextTypeFace(context: Context, attributes: AttributeSet?, textView: TextView) {
        if (textView.typeface != null && textView.typeface.style != 0) {
            return
        }
        val typeArray = context.obtainStyledAttributes(attributes, R.styleable.CustomFontTextView)
        mTypeFaceIndex = typeArray.getInteger(R.styleable.CustomFontTextView_font_name, mTypeFaceIndex)
        if (mTypeFaceIndex in 0..FontType.values().size) {
            textView.typeface = getTypeFace(FontType.values()[mTypeFaceIndex])
        }
        typeArray.recycle()

    }

    /**
     * 根据字体设置textVie显示的字体
     */
    fun setTextTypeFace(textView: TextView, fontType: FontType?) {
        val localTypeFace = getTypeFace(fontType)
        localTypeFace?.let {
            setTextTypeFace(AndyApplication.INSTANCE, null, textView)
        }
    }

    /**
     * 根据名称获取字体类型
     * @return  字体类型
     */
    fun getFontTypeByName(fontName: String): FontType? {
        return FontType.values().firstOrNull { it.fontName == fontName }
    }


    /**
     * 根据字体类型获取字体
     * @param  fontType 字体类型
     */
    fun getTypeFace(fontType: FontType?): Typeface? {
        return fontType?.let {
            var typeFace = mTypeFaceMap[fontType]
            if (typeFace == null) {
                typeFace = Typeface.createFromAsset(AndyApplication.INSTANCE.assets, fontType.path)
                mTypeFaceMap[fontType] = typeFace
            }
            return typeFace
        }
    }

}



