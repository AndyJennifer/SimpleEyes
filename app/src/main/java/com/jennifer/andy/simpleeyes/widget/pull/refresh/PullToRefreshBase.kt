package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper.INVALID_POINTER
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import android.widget.Scroller
import java.lang.RuntimeException


/**
 * Author:  andy.xwt
 * Date:    2018/6/15 10:16
 * Description: 基础刷新父布局。处理竖直方向上的事件分发。处理了多点触摸下的刷新。
 */

abstract class PullToRefreshBase<T : View> : LinearLayout, PullToRefresh<T> {


    protected lateinit var mRootView: T
    protected var mRefreshView: PullRefreshView? = null
    private lateinit var mScroller: Scroller
    private var mTouchSlop: Int = 0

    /**
     * 用来记录y轴上的速度
     */
    private lateinit var mVelocityTracker: VelocityTracker

    /**
     * 刷新View实际高度
     */
    private var mRefreshHeight = 0

    /**
     * 拖动的时候有效的手指id
     */
    private var mActivePointerId = INVALID_POINTER

    /**
     * 当前刷新状态
     */
    var mScrollState = SCROLL_STATE_IDLE

    companion object {
        /**
         * 当前刷新是空闲的
         */
        const val SCROLL_STATE_IDLE = 0

        /**
         * 当前正在刷新
         */
        const val SCROLL_STATE_DRAGGING = 1

        /**
         * 正在隐藏刷新
         */
        const val SCROLL_STATE_SETTLING = 2

        /**
         * 滑动最小接近距离，单位dp
         */
        const val SCROLL_CLOSE_ENOUGH = 2
    }


    /**
     * 最后点击的位置
     */
    private var mLastMotionX = 0f
    private var mLastMotionY = 0f
    private var mInitialMotionX = 0f
    private var mInitialMotionY = 0f

    private var isRefreshEnable = true //是否能下拉刷新
    private var mIsBeingDragged = false//是否将要进行拖拽
    private var mIsUnableToDrag = false//当前不能拖拽 用于判断x y轴移动距离的
    private var mIsScrollStarted = false//内容是否开始滚动


    private val mEndScrollRunnable = Runnable {
        mScrollState = SCROLL_STATE_IDLE
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = LinearLayout.VERTICAL
        mRootView = createRootView(context, attrs)
        mTouchSlop = ViewConfiguration.get(getContext()).scaledTouchSlop
        mScroller = Scroller(getContext())
        mRefreshView = initRefreshView()
        addView(mRefreshView, 0)
        addView(mRootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mRefreshView != null) {
            //获取刷新view的高度
            measureChild(mRefreshView, widthMeasureSpec, heightMeasureSpec)
            mRefreshHeight = mRefreshView!!.measuredHeight

            val layoutParams = mRefreshView?.layoutParams as LinearLayout.LayoutParams
            layoutParams.topMargin = -mRefreshHeight
            mRefreshView?.layoutParams = layoutParams
        } else {
            throw RuntimeException("you not set refreshView!!")
        }

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (isPullToRefreshEnabled()) {
            val action = event.action
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                //重置
                mIsBeingDragged = false
                return false
            }

            if (action != MotionEvent.ACTION_DOWN) {
                if (mIsBeingDragged) {//如果将要进行拖拽，就拦截
                    return true
                }
                if (mIsUnableToDrag) {//如果不能被拖拽，就不拦截
                    return false
                }
            }
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    if (isReadyForPullStart()) {
                        mLastMotionX = event.x
                        mInitialMotionX = event.x
                        mLastMotionY = event.y
                        mInitialMotionY = event.y

                        //获取有效手指 第一个有效手指的角标总是0
                        mActivePointerId = event.getPointerId(0)

                        mIsUnableToDrag = false
                        //在点击的时候，判断当前刷新界面是否下拉显示，或者在回退的路上
                        if (mScrollState == SCROLL_STATE_SETTLING && getScrollDiff() > SCROLL_CLOSE_ENOUGH) {
                            mIsBeingDragged = true
                            mScrollState = SCROLL_STATE_DRAGGING
                        } else {
                            mIsBeingDragged = false
                        }
                    }

                }
                MotionEvent.ACTION_MOVE -> {

                    //获取有效的手指的距离
                    val actionPointerId = mActivePointerId
                    if (mActivePointerId != INVALID_POINTER) {

                        val pointerIndex = event.findPointerIndex(actionPointerId)
                        val x = event.getX(pointerIndex)
                        val xAbs = Math.abs(x - mInitialMotionX)

                        val y = event.getY(pointerIndex)
                        val dy = y - mLastMotionY
                        val yAbs = Math.abs(dy)

                        //如果竖直移动距离大于水平移动距离且为下拉事件，设置当前为拖动状态
                        if (yAbs > mTouchSlop && yAbs * 0.5 > xAbs && dy > 0) {
                            mIsBeingDragged = true
                            mScrollState = SCROLL_STATE_DRAGGING
                            mLastMotionX = x
                            mLastMotionY = if (dy > 0) mInitialMotionY + mTouchSlop else mInitialMotionY - mTouchSlop
                        } else if (xAbs > mTouchSlop) {
                            mIsUnableToDrag = true
                        }
                    }
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    onSecondaryPointerUp(event)
                }
            }

        }
        return mIsBeingDragged
    }

    /**
     * 重新赋值有效的手指，并记录抬起的手指的最后的y轴距离
     */
    private fun onSecondaryPointerUp(event: MotionEvent) {
        val pointerIndex = event.actionIndex
        val pointerId = event.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mLastMotionY = event.getY(newPointerIndex)
            mActivePointerId = event.getPointerId(newPointerIndex)

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && event.edgeFlags != 0)
            return false
        if (mRefreshView == null) {
            return false
        }
        val action = event.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_CANCEL -> {
                if (mIsBeingDragged) {
                    //todo 如果取消了，你就回去
                }
            }
            MotionEvent.ACTION_DOWN -> {
                //这个时候要停止刷新的滚动
                mLastMotionX = event.x
                mInitialMotionX = event.x
                mLastMotionY = event.y
                mInitialMotionY = event.y

                //获取有效手指 第一个有效手指的角标总是0
                mActivePointerId = event.getPointerId(0)
                mScroller.abortAnimation()
            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsBeingDragged) {
                    //获取有效的手指的距离
                    val actionPointerId = mActivePointerId
                    if (mActivePointerId != INVALID_POINTER) {

                        val pointerIndex = event.findPointerIndex(actionPointerId)
                        val x = event.getX(pointerIndex)
                        val xAbs = Math.abs(x - mLastMotionX)

                        val y = event.getY(pointerIndex)
                        val dy = y - mLastMotionY
                        val yAbs = Math.abs(dy)

                        //如果竖直移动距离大于水平移动距离，设置当前为拖动状态
                        if (yAbs > xAbs) {
                            mIsBeingDragged = true
                            mScrollState = SCROLL_STATE_DRAGGING
                            mLastMotionX = x
                            mLastMotionY = y
                            performDrag(dy)
                        }


                    }

                }
            }
            MotionEvent.ACTION_UP -> {
                if (mIsBeingDragged) {
                    //todo 判断当前高度如果小于，刷新你就回去，否则执行刷新操作，监听刷新失败成功的方法。不管结果都滚动回去
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                val y = event.getY(index)
                val x = event.getX(index)
                mLastMotionY = y
                mLastMotionX = x
                mActivePointerId = event.getPointerId(index)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(event)
            }
        }

        return true
    }

    /**
     * 开始滚动
     */
    private fun performDrag(dy: Float) {
        if (Math.abs(scrollY - dy) <= mRefreshHeight) {//控制headView的显示高度
            scrollBy(0, -dy.toInt())
        }
        dispatchPullEvent(dy)//将竖直事件分发出去
    }

    override fun computeScroll() {
        mIsScrollStarted = true
        //如果当前正在回弹，取消回弹
        if (!mScroller.isFinished && mScroller.computeScrollOffset()) {
            val oldX = scrollX
            val oldY = scrollY
            val x = mScroller.currX
            val y = mScroller.currY
            if (oldX != x || oldY != y) {
                mScroller.abortAnimation()
                scrollTo(x, y)
            }
            ViewCompat.postInvalidateOnAnimation(this)
            return
        }
        //处理自身滑动
        completeScroll(true)
    }

    private fun completeScroll(postEvents: Boolean) {
        val needPopulate = mScrollState == SCROLL_STATE_SETTLING
        if (needPopulate) {
            val wasScrolling = !mScroller.isFinished
            if (wasScrolling) {
                mScroller.abortAnimation()
                val oldX = scrollX
                val oldY = scrollY
                val x = mScroller.currX
                val y = mScroller.currY
                if (oldX != x || oldY != y) {
                    scrollTo(x, y)
                }
            }
        }
        if (needPopulate) {
            if (postEvents) {
                ViewCompat.postInvalidateOnAnimation(this)
            }
        }
    }

    /** 弹性滑动
     * @param x 水平滑动距离
     * @param y 竖直方向就
     * @param velocity y轴上的速度
     */
    private fun smoothScrollTo(x: Int, y: Int, velocity: Int) {

    }

    /**
     * 获取当前滑动的差值
     */
    private fun getScrollDiff(): Int {
        return Math.abs(mScroller.finalY - mScroller.currY)
    }

    /**
     * 处理下拉事件
     */
    open fun dispatchPullEvent(dy: Float) {
    }


    /**
     * 是否允许下拉刷新
     */
    override fun isPullToRefreshEnabled() = isRefreshEnable

    /**
     * 创建根布局
     */
    abstract fun createRootView(context: Context, attrs: AttributeSet?): T


    /**
     * 是否准备好下拉
     */
    abstract fun isReadyForPullStart(): Boolean


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!mScroller.isFinished) {
            mScroller.abortAnimation()
        }
    }


}