package com.jennifer.andy.simpleeyes.widget.viewpager

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs


/**
 * Author:  andy.xwt
 * Date:    2018/6/14 16:03
 * Description:
 * 该ViewPager主要拦截两个情况：
 * 1.拦截向上的滑动
 * 2.拦截固定角标的向左滑动
 *
 */
class InterceptVerticalViewPager : androidx.viewpager.widget.ViewPager {

    private var mLastMotionX: Float = 0f
    private var mLastMotionY: Float = 0f

    lateinit var verticalListener: () -> Unit
    lateinit var horizontalListener: (Int) -> Unit
    private var isDoListener: Boolean = false

    var mDisMissIndex = -1

    private val scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    constructor (context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action and MotionEvent.ACTION_MASK
        val y = ev.y
        val x = ev.x
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionX = ev.x
                mLastMotionY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = mLastMotionX - x
                val dy = mLastMotionY - y
                val absX = abs(dx)
                val absY = abs(dy)

                //向上滑动，且y轴坐标的距离大于x轴滑动距离的一半
                if (dy > 0 && absY > absX * 0.5f && absY > scaledTouchSlop) {
                    if (!isDoListener) {
                        verticalListener()
                        isDoListener = true
                        return false
                    }
                }
                //像左滑动，且x轴坐标的距离大于y轴滑动距离的一半
                if (dx > 0 && absX > absY * 0.5f && absX > scaledTouchSlop) {
                    if (!isDoListener && mDisMissIndex == currentItem) {
                        horizontalListener(mDisMissIndex)
                        isDoListener = true
                        return false
                    }
                }
                mLastMotionX = x
                mLastMotionY = y
            }
        }

        return super.dispatchTouchEvent(ev)
    }
}
