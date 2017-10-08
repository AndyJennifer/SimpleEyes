package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/9/27 22:01
 * Description:
 */

class BottomItemLayout : LinearLayout {


    private var mSelectedTextColor = resources.getColor(R.color.colorPrimaryDark)
    private var mUnselectedTextColor = resources.getColor(R.color.SecondaryText)

    private lateinit var mIcon: ImageView
    private lateinit var mIconTitle: TextView
    private var mSelectedDrawable: Drawable? = null
    private var mUnSelectedDrawable: Drawable? = null
    private var mTitle: String? = null

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
        gravity = Gravity.CENTER
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            if (mSelectedDrawable != null) {
                mIcon.setImageDrawable(mSelectedDrawable)
                mIconTitle.setTextColor(mSelectedTextColor)
            }
        } else {
            if (mUnSelectedDrawable != null) {
                mIcon.setImageDrawable(mUnSelectedDrawable)
                mIconTitle.setTextColor(mUnselectedTextColor)
            }
        }
    }

    fun setTabPosition(position: Int) {
        mTabPosition = position
        if (position == 0) {
            isSelected = true
        }
    }

    fun getTabPosition() = mTabPosition

    fun setBottomItem(bottomItem: BottomItem) {
        //设置选中图片
        if (bottomItem.mSelectResource > -1) {
            mSelectedDrawable = resources.getDrawable(bottomItem.mSelectResource)
        } else if (bottomItem.mSelectDrawable != null) {
            mSelectedDrawable = bottomItem.mSelectDrawable!!
        }
        //设置未被选中团片
        if (bottomItem.mUnSelectedResource > -1) {
            mUnSelectedDrawable = resources.getDrawable(bottomItem.mUnSelectedResource)
        } else if (bottomItem.mUnSelectedDrawable != null) {
            mUnSelectedDrawable = bottomItem.mUnSelectedDrawable!!
        }
        //设置标题
        if (bottomItem.mTitleResource > -1) {
            mTitle = resources.getString(bottomItem.mTitleResource)
        } else if (bottomItem.mTitle != null) {
            mTitle = bottomItem.mTitle!!
        }
        //初始化
        mIcon.setImageDrawable(mUnSelectedDrawable)
        mIconTitle.text = mTitle

    }


}