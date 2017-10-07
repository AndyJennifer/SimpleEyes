package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/9/27 22:01
 * Description:
 */

class BottomItemLayout : FrameLayout {


    private var mSelectedTextColor = resources.getColor(R.color.colorPrimaryDark)
    private var mUnselectedTextColor = resources.getColor(R.color.SecondaryText)

    private lateinit var mIcon: ImageView
    private lateinit var mIconTitle: TextView
    private lateinit var mSelectedDrawable: Drawable
    private lateinit var mUnSelectedDrawable: Drawable
    private lateinit var mTitle: String

    private var mTabPosition = -1

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_item, this, true)
        mIcon = view.findViewById(R.id.iv_image)
        mIconTitle = view.findViewById(R.id.tv_title)
        mIconTitle.text = mTitle
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            mIcon.setImageDrawable(mSelectedDrawable)
            mIconTitle.setTextColor(mSelectedTextColor)
        } else {
            mIcon.setImageDrawable(mUnSelectedDrawable)
            mIconTitle.setTextColor(mUnselectedTextColor)
        }
    }

    fun setTabPosition(position: Int) {
        mTabPosition = position
        if (position == 0) {
            isSelected = true
        }
    }

    fun getTabPosition() = mTabPosition

}