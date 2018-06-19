package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView


/**
 * Author:  andy.xwt
 * Date:    2018/6/19 22:24
 * Description:SmoothScrollerToTop 平滑滚动式 滚动位置的View与RecyclerView顶部对齐
 */

class LinearLayoutManagerWithSmoothScroller : LinearLayoutManager {

    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)

    }

    inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@LinearLayoutManagerWithSmoothScroller.computeScrollVectorForPosition(targetPosition)
        }

        /**
         * 默认顶部与RecyclerView对齐
         */
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

    }

}