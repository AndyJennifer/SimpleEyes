package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.view.animation.AccelerateInterpolator


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

        private val TARGET_SEEK_SCROLL_DISTANCE_PX = 10000
        private val TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2f

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@LinearLayoutManagerWithSmoothScroller.computeScrollVectorForPosition(targetPosition)
        }

        /**
         * 默认顶部与RecyclerView对齐
         */
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        /**
         * 重写了滚动的时候按照加速的方式
         */
        override fun updateActionForInterimTarget(action: Action) {
            val scrollVector = computeScrollVectorForPosition(targetPosition)
            if (scrollVector == null || scrollVector.x == 0f && scrollVector.y == 0f) {
                val target = targetPosition
                action.jumpTo(target)
                stop()
                return
            }
            normalize(scrollVector)
            mTargetVector = scrollVector

            mInterimTargetDx = (TARGET_SEEK_SCROLL_DISTANCE_PX * scrollVector.x).toInt()
            mInterimTargetDy = (TARGET_SEEK_SCROLL_DISTANCE_PX * scrollVector.y).toInt()
            val time = calculateTimeForScrolling(TARGET_SEEK_SCROLL_DISTANCE_PX)
            action.update((mInterimTargetDx * TARGET_SEEK_EXTRA_SCROLL_RATIO).toInt(),
                    (mInterimTargetDy * TARGET_SEEK_EXTRA_SCROLL_RATIO).toInt(),
                    (time * TARGET_SEEK_EXTRA_SCROLL_RATIO).toInt(), AccelerateInterpolator())//添加加速
        }

    }

}