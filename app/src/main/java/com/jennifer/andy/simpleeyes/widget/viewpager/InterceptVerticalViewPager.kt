package com.jennifer.andy.simpleeyes.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration


/**
 * Author:  andy.xwt
 * Date:    2018/6/14 16:03
 * Description: 拦截竖直滑动的ViewPager
 */
class InterceptVerticalViewPager : ViewPager {

    private var mLastMotionX: Float = 0f
    private var mLastMotionY: Float = 0f

    lateinit var verticalListener: () -> Unit

    constructor (context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action and MotionEvent.ACTION_MASK
        val y = ev.y
        val x = ev.x
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionX = ev.x
                mLastMotionY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val xDiff = Math.abs(x - mLastMotionX)
                val yDiff = Math.abs(y - mLastMotionY)
                if (yDiff > xDiff * 0.5f && yDiff > ViewConfiguration.get(context).scaledTouchSlop) {
                    verticalListener()
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


}
