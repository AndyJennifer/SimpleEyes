package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.support.v4.widget.ViewDragHelper.INVALID_POINTER
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import android.widget.Scroller
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2018/6/15 10:16
 * Description: 基础刷新父布局。处理竖直方向上的事件分发。处理了多点触摸下的刷新。
 */

abstract class PullToRefreshBase<T : View> : LinearLayout, PullToRefresh<T> {


    private lateinit var mRootView: T
    private var mRefreshView: View? = null
    private var mScroller: Scroller? = null
    private var mTouchSlop: Int = 0

    /**
     * 拖动的时候有效的手指id
     */
    private var mActivePointerId = INVALID_POINTER

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

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mRootView = createRootView(context, attrs)
        mTouchSlop = ViewConfiguration.get(getContext()).scaledTouchSlop
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PullToRefreshView)
        attrs?.let {
            val layoutInflater = LayoutInflater.from(context)
            val refreshViewResId = typeArray.getResourceId(R.styleable.PullToRefreshView_refreshView, 0)
            if (refreshViewResId > 0)
                mRefreshView = layoutInflater.inflate(refreshViewResId, null, false)

        }
        typeArray.recycle()
        addView(mRootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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
            when (action) {
                MotionEvent.ACTION_DOWN -> {

                    mLastMotionX = event.x
                    mInitialMotionX = event.x
                    mLastMotionY = event.y
                    mInitialMotionY = event.y

                    //获取有效手指 第一个有效手指的角标总是0
                    mActivePointerId = event.getPointerId(0)
                    mIsUnableToDrag = false
                    mIsBeingDragged = true

                    //todo 判断当前刷新界面是否下拉显示，或者在回退的路上

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

                        //如果竖直移动距离大于水平移动距离，设置当前为拖动状态
                        if (yAbs > mTouchSlop && yAbs * 0.5 > xAbs) {
                            mIsBeingDragged = true
                            mScrollState = SCROLL_STATE_DRAGGING
                            mLastMotionX = x
                            mLastMotionY = if (dy > 0) mInitialMotionY + mTouchSlop else mLastMotionY - mTouchSlop
                        } else if (xAbs > mTouchSlop) {
                            mIsUnableToDrag = true
                        }

                        if (mIsBeingDragged) {
                            // todo 开始准备滑动了
                        }
                    }
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    onSecondaryPointerUp(event)
                }
            }

        }
        return super.onInterceptTouchEvent(event)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //todo 这里开始处理事件。可以将事件分发出去。那边的根据自己情况判断，是否需要刷新就行了，自己要处理个头部刷新的基类出来。
        return super.onTouchEvent(event)
    }

    override fun isPullToRefreshEnabled() = isRefreshEnable

    /**
     * 创建根布局
     */
    abstract fun createRootView(context: Context, attrs: AttributeSet?): T
}