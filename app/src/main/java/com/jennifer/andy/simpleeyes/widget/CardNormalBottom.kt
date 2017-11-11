package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/11/6 16:32
 * Description:卡片底部默认布局，包含图标，标题，种类，时间。是否精选
 */

class CardNormalBottom : FrameLayout {


    private lateinit var mIcon: SimpleDraweeView
    private lateinit var mTvTitle: TextView
    private lateinit var mTvDescription: TextView
    private lateinit var mBtnMoreOperate: ImageButton
    private lateinit var mLine: View

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_card_normal_bottom, this, true)
        mIcon = view.findViewById(R.id.iv_source)
        mTvTitle = view.findViewById(R.id.tv_title)
        mTvDescription = view.findViewById(R.id.tv_desc)
        mBtnMoreOperate = view.findViewById(R.id.btn_reply_more)
        mLine = view.findViewById(R.id.view_line)
    }


    /**
     * 设置图片显示
     * @param url 图片地址
     */
    fun setIconUrl(url: String) {
        mIcon.setImageURI(url)
    }

    /**
     * 设置图标是否可见
     */
    fun setIconVisible(visible: Boolean) {
        mIcon.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置图标是否是圆形
     */
    fun setIconType(isCircle: Boolean) {
        if (isCircle) mIcon.hierarchy.roundingParams = RoundingParams.asCircle()
        else mIcon.hierarchy.roundingParams?.roundAsCircle = false
    }

    /**
     * 设置底部线是否可见
     */
    fun setLineVisible(visible: Boolean) {
        mLine.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        mTvTitle.text = title
    }

    /**
     * 设置描述
     */
    fun setDescription(desc: String) {
        mTvDescription.text = desc
    }

    fun setMoreOperateVisible(visible: Boolean) {
        mBtnMoreOperate.visibility = if (visible) View.VISIBLE else View.GONE
    }


}