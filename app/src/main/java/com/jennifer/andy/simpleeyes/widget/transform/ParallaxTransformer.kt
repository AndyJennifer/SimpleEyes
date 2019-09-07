package com.jennifer.andy.simpleeyes.widget.transform

import androidx.viewpager.widget.ViewPager
import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2018/4/30 01:33
 * Description:视差切换效果
 */

class ParallaxTransformer : androidx.viewpager.widget.ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val width = page.width
        if (position < -1) {
            page.scrollX = (-width).toInt()
        } else if (position < 1) {
            if (position < 0) {//(-1,0)
                page.scrollX = (width * 0.6 * position).toInt()
            } else {//[0,1]
                page.scrollX = (width * 0.6 * position).toInt()
            }
        } else {
            page.scrollX = (width).toInt()
        }
    }
}