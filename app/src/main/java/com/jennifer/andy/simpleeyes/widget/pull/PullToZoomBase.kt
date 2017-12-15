package com.jennifer.andy.simpleeyes.widget.pull

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.ScreenUtils


/**
 * Author:  andy.xwt
 * Date:    2017/11/11 16:22
 * Description:下拉变焦基础类
 */

abstract class PullToZoomBase<T : View> : LinearLayout, PullToZoom<T> {

    private var mTouchSlop = 0
    private var mScreenHeight = 0
    private var mScreenWidth = 0

    protected lateinit var mRootView: T
    protected var mZoomView: View? = null
    protected var mHeadView: View? = null

    private var isParallax = true
    private var isZoomEnable = true
    private var isZooming = false
    private var isHideHeader = false

    private var mIsBeingDragged = false//是否被拖拽

    private var mInterceptPressedX = 0f
    private var mInterceptPressedY = 0f
    private var mTouchPressedX = 0f
    private var mTouchPressedY = 0f

    private val DAMPING = 3f//阻尼系数

    private var mPullZoomListener: onPullZoomListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        gravity = Gravity.CENTER
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        mScreenHeight = ScreenUtils.getScreenHeight(context)
        mScreenWidth = ScreenUtils.getScreenHeight(context)
        mRootView = createRootView(context, attrs)

        attrs?.let {
            val layoutInflater = LayoutInflater.from(context)
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PullToZoomView)
            val zoomViewResId = typeArray.getResourceId(R.styleable.PullToZoomView_zoomView, 0)
            val headViewResId = typeArray.getResourceId(R.styleable.PullToZoomView_headView, 0)
            isParallax = typeArray.getBoolean(R.styleable.PullToZoomView_isHeaderParallax, true)
            if (zoomViewResId > 0) {
                mZoomView = layoutInflater.inflate(zoomViewResId, null, false)
            }
            if (headViewResId > 0) {
                mHeadView = layoutInflater.inflate(headViewResId, null, false)
            }
            handleStyledAttributes(typeArray)
            typeArray.recycle()
        }
        addView(mRootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    /**
     * 处理竖直拖动滑动监听,并记录当前拖动的状态
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (isPullToZoomEnabled() && !isHideHeader) {
            val action = event.action
            //如果是取消或者手指抬起不拦截
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                mIsBeingDragged = false
                return false
            }
            //如果正在拖拽，拦截
            if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
                return true
            }
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    val dy: Float
                    val dx: Float
                    val currentX = event.x
                    val currentY = event.y
                    dy = currentY - mInterceptPressedY
                    dx = currentX - mInterceptPressedX
                    if (dy > mTouchSlop && (Math.abs(dy) > Math.abs(dx))) {
                        mInterceptPressedX = currentX
                        mInterceptPressedY = currentY
                        mIsBeingDragged = true
                    }

                }
                MotionEvent.ACTION_DOWN -> {
                    if (isReadyForPullStart()) {
                        mInterceptPressedX = event.x
                        mInterceptPressedY = event.y
                        mIsBeingDragged = false
                        mTouchPressedY = event.y
                        mTouchPressedX = event.x
                    }
                }
            }
        }

        return mIsBeingDragged
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isPullToZoomEnabled() && !isHideHeader()) {
            //如果是在边缘也不拦截
            if (event.action == MotionEvent.ACTION_DOWN && event.edgeFlags != 0) {
                return false
            }
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (mIsBeingDragged) {
                        mInterceptPressedY = event.y
                        mInterceptPressedX = event.x
                        dispatchPullEvent()
                        isZooming = true
                        return true
                    }
                }
                MotionEvent.ACTION_DOWN -> {
                    if (isReadyForPullStart()) {
                        mTouchPressedX = event.x
                        mTouchPressedY = event.y
                        return true
                    }
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    if (mIsBeingDragged) {
                        mIsBeingDragged = false
                        if (isZooming()) {
                            smoothScrollToTop()
                            isZooming = false
                            mPullZoomListener?.onPullZoomEnd()
                        }
                    }
                    return true

                }
            }
        }
        return false
    }


    /**
     * 创建根布局
     */
    abstract fun createRootView(context: Context, attrs: AttributeSet?): T

    /**
     * 是否准备好下拉
     */
    abstract fun isReadyForPullStart(): Boolean

    /**
     * 下拉变焦头布局
     */
    abstract fun pullHeadToZoom(scrollValue: Int)

    /**
     * 平滑滑动到顶部
     */
    abstract fun smoothScrollToTop()

    /**
     * 设置头view
     */
    abstract fun setHeaderView(headerView: View?)

    /**
     * 设置头布局的高度
     */
    abstract fun setHeaderViewLayoutParams(layoutParams: LinearLayout.LayoutParams)

    /**
     * 设置变焦view
     */
    abstract fun setZoomView(zoomView: View?)

    /**
     * 处理下拉事件
     */
    private fun dispatchPullEvent() {
        val scrollValue = Math.round(Math.min(mTouchPressedY - mInterceptPressedY, 0f) / DAMPING)
        pullHeadToZoom(scrollValue)
        mPullZoomListener?.onPullZooming(Math.abs(scrollValue))
    }


    override fun getZoomView() = mZoomView

    override fun getHeaderView() = mHeadView

    override fun getPullRootView(): T = mRootView

    override fun isPullToZoomEnabled(): Boolean = isZoomEnable

    override fun isZooming(): Boolean = isZooming

    override fun isParallax(): Boolean = isParallax

    override fun isHideHeader(): Boolean = isHideHeader

    fun setHideHeader(isHideHeader: Boolean) {
        this.isHideHeader = isHideHeader
    }

    fun setIsParallax(isParallax: Boolean) {
        this.isParallax = isParallax
    }

    fun setIsZoomEnable(isZoomEnable: Boolean) {
        this.isZoomEnable = isZoomEnable
    }


    fun setOnPullZoomListener(onPullZoomListener: onPullZoomListener) {
        mPullZoomListener = onPullZoomListener
    }

    /**
     * 下拉变焦监听
     */
    interface onPullZoomListener {
        /**
         * 正在变焦
         * @param scrollValue 滑动距离
         */
        fun onPullZooming(scrollValue: Int)

        /**
         * 变焦结束
         */
        fun onPullZoomEnd()
    }
}