package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_blank_card.view.*


/**
 * Author:  andy.xwt
 * Date:    2019-06-24 12:16
 * Description:
 */

class StickyNavLayout : LinearLayout, NestedScrollingParent2 {


    private var mScrollingParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 有嵌套滑动到来了，判断父view是否接受嵌套滑动
     *
     * @param child 嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target 具体嵌套滑动的那个子类
     * @param axes   支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @param type 导致此滚动事件的输入类型
     */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int) = true

    /**
     * 当父view接受嵌套滑动，当onStartNestedScroll方法返回true该方法会调用
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mScrollingParentHelper.onStopNestedScroll(view, type)
    }

}
