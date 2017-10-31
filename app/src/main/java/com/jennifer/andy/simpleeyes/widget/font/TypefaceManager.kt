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

class TypefaceManager() {

    companion object {

        private var mTypeFaceMap: MutableMap<TypefaceManager.FontType, Typeface> = mutableMapOf()

        private var mTypeFaceIndex: Int = FontType.NORMAL.ordinal

        /**
         * 设置textView字体,如果参数中有字体，就采用本身的，如果没有就
         *
         *@param context
         *@param attributes
         *@param textView
         */
        fun setTextTypeFace(context: Context, attributes: AttributeSet, textView: TextView) {
            val typeArray = context.obtainStyledAttributes(attributes, R.styleable.CustomFontTextView)
            mTypeFaceIndex = typeArray.getInteger(R.styleable.CustomFontTextView_font_name, mTypeFaceIndex)
            typeArray.recycle()
            if (textView.typeface != null && textView.typeface.style != 0) {
                return
            }
            if ((mTypeFaceIndex >= 0) && mTypeFaceIndex < TypefaceManager.FontType.values().size) {
                textView.typeface = getTypeFace(TypefaceManager.FontType.values()[mTypeFaceIndex])
            }
        }

        /**
         * 根据字体类型获取字体
         * @param  fontType 字体类型
         */
        private fun getTypeFace(fontType: TypefaceManager.FontType): Typeface {
            var typeFace = mTypeFaceMap[fontType]
            if (typeFace == null) {
                typeFace = Typeface.createFromAsset(AndyApplication.mApplication.assets, fontType.path)
                return typeFace
            }
            mTypeFaceMap.put(fontType, typeFace)
            return typeFace
        }

    }

    /**
     * 字体常量
     */
    enum class FontType(val path: String) {
        BOLD("font/FZLanTingHeiS-DB1-GB-Regular.TTF"),
        FUTURE("Futura-CondensedMedium.ttf"),
        LOBSTER("font/Lobster-1.4.otf"),
        NORMAL("FZLanTingHeiS-L-GB-Regular.TTF")
    }
}
