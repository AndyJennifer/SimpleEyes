package com.jennifer.andy.simpleeyes.widget

import android.graphics.drawable.Drawable


/**
 * Author:  andy.xwt
 * Date:    2017/10/7 22:52
 * Description:
 */

class BottomItem{


    var mSelectDrawable: Drawable? = null
    var mSelectResource = -1

    var mTitleResource = -1
    var mTitle: String? = null

    var mUnSelectedDrawable: Drawable? = null
    var mUnSelectedResource = -1


    constructor(selectDrawable: Drawable, title: String) {
        this.mSelectDrawable = selectDrawable
        this.mTitle = title
    }

    constructor(selectResource: Int, title: String) {
        this.mSelectResource = selectResource
        this.mTitle = title
    }

    constructor(mSelectDrawable: Drawable, mTitleResource: Int) {
        this.mSelectDrawable = mSelectDrawable
        this.mTitleResource = mTitleResource
    }

    constructor(mSelectResource: Int, mTitleResource: Int) {
        this.mSelectResource = mSelectResource
        this.mTitleResource = mTitleResource
    }


    fun setUnSelectedDrawable(unSelectedDrawable: Drawable) {
        this.mUnSelectedDrawable = unSelectedDrawable
    }

    fun setUnSelectedDrawable(unSelectedResource: Int) {
        this.mUnSelectedResource = unSelectedResource
    }

}