package com.jennifer.andy.simpleeyes.widget

import android.graphics.drawable.Drawable


/**
 * Author:  andy.xwt
 * Date:    2017/10/7 22:52
 * Description:
 */

class BottomItem {


    private lateinit var mSelectDrawable: Drawable
    private var mSelectResource = -1

    private var mTitleResource = -1
    private lateinit var mTitle: String

    private lateinit var mUnSelectedDrawable: Drawable
    private var mUnSelectedResource = -1


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