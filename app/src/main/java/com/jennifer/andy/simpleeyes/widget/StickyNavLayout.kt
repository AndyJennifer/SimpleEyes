package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.jennifer.andy.simpleeyes.R
import kotlinx.android.synthetic.main.layout_blank_card.view.*


/**
 * Author:  andy.xwt
 * Date:    2019-06-24 12:16
 * Description: 粘性头部+导航栏+viewPager父控件
 */

class StickyNavLayout : LinearLayout, NestedScrollingParent2 {

    private var mScrollingParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)

    private lateinit var mTopView: View//头部view
    private lateinit var mNavView: View//导航view
    private lateinit var mViewPager: View//viewpager

    private var mCanScrollDistance: Float = 0f

    private var mListener: ScrollChangeListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = VERTICAL
    }


    /**
     * 有嵌套滑动到来了，判断父view是否接受嵌套滑动
     *
     * @param child 嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target 具体嵌套滑动的那个子类
     * @param axes   支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @param type  滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int) = true

    /**
     * 当父view接受嵌套滑动，当onStartNestedScroll方法返回true该方法会调用
     * @param type  滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    /**
     * 在嵌套滑动的子View未滑动之前，判断父view是否优先与子view处理(也就是父view可以先消耗，然后给子view消耗）
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离 dy<0向下滑动 dy>0 向上滑动
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     * @param type  滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //这里不管是手势滑动还是fling都需要父控件处理，因为子控件可能会出现手势滑动->手势滑动->fling,
        //如果这里只处理手势滚动，那么就会出现，父控件滚动一定距离，子控件再fling的问题。

        //如果子view欲向上滑动，则先交给父view滑动
        val hideTop = dy > 0 && scrollY < mCanScrollDistance
        //如果子view预向下滑动，必须要子view不能向下滑动后，才能交给父view滑动
        val showTop = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)

        if (hideTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }

    }


    /**
     * 嵌套滑动的子View在滑动之后，判断父view是否继续处理（也就是父消耗一定距离后，子再消耗，最后判断父消耗不）
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param type  滑动类型，ViewCompat.TYPE_NON_TOUCH fling 效果ViewCompat.TYPE_TOUCH 手势滑动
     */
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    /**
     * 嵌套滑动时，如果父View处理了fling,那子view就没有办法处理fling了，所以这里要返回为false
     */
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float) = false


    override fun onFinishInflate() {
        super.onFinishInflate()
        mTopView = findViewById(R.id.id_sticky_nav_layout_top_view)
        mNavView = findViewById(R.id.id_sticky_nav_layout_nav_view)
        mViewPager = findViewById(R.id.id_sticky_nav_layout_viewpager)
        if (mViewPager !is ViewPager) {
            throw  RuntimeException("id_sticky_nav_layout_viewpager should be viewpager!")
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //ViewPager修改后的高度= 总高度-导航栏高度
        val layoutParams = mViewPager.layoutParams
        layoutParams.height = measuredHeight - mNavView.measuredHeight
        mViewPager.layoutParams = layoutParams
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //可滑动高度为，topView的高度- 标题栏的高度
        mCanScrollDistance = (mTopView.measuredHeight - resources.getDimension(R.dimen.normal_title_height).toInt()).toFloat()
    }

    override fun scrollTo(x: Int, y: Int) {

        //控制父view滑动范围为0-mCanScrollDistance.之间
        var adjustY = y
        if (y < 0) adjustY = 0
        if (y > mCanScrollDistance) adjustY = mCanScrollDistance.toInt()

        //将移动比例传出去
        Log.d("wtf", "adJustY--> $adjustY   $mCanScrollDistance   --->${adjustY / mCanScrollDistance}")
        mListener?.onScroll(adjustY / mCanScrollDistance)

        if (adjustY != scrollY) super.scrollTo(x, adjustY)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mScrollingParentHelper.onStopNestedScroll(view, type)
    }

    override fun getNestedScrollAxes(): Int {
        return mScrollingParentHelper.nestedScrollAxes
    }


    interface ScrollChangeListener {
        /**
         * 移动监听
         * @param moveRatio 移动比例
         */
        fun onScroll(moveRatio: Float)
    }

    fun setScrollChangeListener(scrollChangeListener: ScrollChangeListener) {
        mListener = scrollChangeListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mListener = null
    }
}
