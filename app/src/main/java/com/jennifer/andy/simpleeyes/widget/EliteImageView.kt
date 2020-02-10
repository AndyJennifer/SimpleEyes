package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView


/**
 * Author:  andy.xwt
 * Date:    2017/11/3 17:44
 * Description:精选imageView 包含翻译图片 翻译
 */

class EliteImageView : FrameLayout {

    private lateinit var ivImageView: SimpleDraweeView
    private lateinit var tvTranslate: CustomFontTextView
    private lateinit var ivArrow: ImageView
    private lateinit var ivDaily: ImageView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_choiceness, this, true)
        ivImageView = findViewById(R.id.iv_elite_image)
        tvTranslate = findViewById(R.id.tv_translate)
        ivArrow = findViewById(R.id.iv_arrow)
        ivDaily = findViewById(R.id.iv_daily)
    }

    /**
     * 设置箭头是否可见
     *
     * @param visible 是否可见
     */
    fun setArrowVisible(visible: Boolean) {
        ivArrow.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置精选是否可见
     */
    fun setDailyVisible(visible: Boolean) {
        ivDaily.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置翻译文字
     */
    fun setTranslateText(text: String) {
        if (!TextUtils.isEmpty(text)) {
            tvTranslate.visibility = View.VISIBLE
            tvTranslate.text = text
        } else {
            tvTranslate.visibility = View.GONE
        }
    }

    /**
     * 设置图片显示
     * @param url 图片地址
     */
    fun setImageUrl(url: String) {
        ivImageView.setImageURI(url)
    }


}