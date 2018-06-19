package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Author:  andy.xwt
 * Date:    2018/6/19 14:37
 * Description:下拉刷新RecyclerView
 */

class PullToRefreshRecyclerView : PullToRefreshBase<RecyclerView> {

    var isShouldScroll = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createRootView(context: Context, attrs: AttributeSet?): RecyclerView = RecyclerView(context, attrs)


    /**
     * 判断当前recyclerView是否滑动到顶部，如果是在顶部就可以进行下拉
     */
    override fun isReadyForPullStart(): Boolean {
        val adapter = mRootView.adapter
        val layoutManager = mRootView.layoutManager as LinearLayoutManager
        if (adapter == null || adapter.itemCount == 0) {
            return true
        } else {
            val into = intArrayOf(0, 0)
            if (layoutManager != null)
                into[0] = layoutManager.findFirstVisibleItemPosition()
            if (into.isNotEmpty() && into[0] <= 1) {
                val firstVisibleChild = mRootView.getChildAt(0)
                if (firstVisibleChild != null) {
                    return firstVisibleChild.top >= mRootView.top
                }
            }
        }
        return false

    }


    /**
     * 设置适配器与布局管理器
     */
    fun setAdapterAndLayoutManager(adapter: BaseQuickAdapter<*, *>, layoutManager: LinearLayoutManager) {
        mRootView.adapter = adapter
        mRootView.layoutManager = layoutManager
        mRootView.itemAnimator = DefaultItemAnimator()
    }

    /**
     * 获取当前RecyclerView
     */
    fun getRootRecyclerView() = mRootView

    /**
     * 滚动到相应位置，判断
     */
    fun scrollToPosition(position: Int) {
        val firstItem = mRootView.getChildLayoutPosition(mRootView.getChildAt(0))
        val lastItem = mRootView.getChildLayoutPosition(mRootView.getChildAt(mRootView.childCount - 1))

        when {
            position <= firstItem -> {//小于第一个可见范围
                isShouldScroll = false
                mRootView.smoothScrollToPosition(position)
            }
            position <= lastItem -> {//在第一个与最后一个之间
                val movePosition = position - firstItem
                if (movePosition >= 0 && movePosition < mRootView.childCount - 1) {
                    val top = mRootView.getChildAt(movePosition).top
                    mRootView.smoothScrollBy(0, top)
                }
                isShouldScroll = false
            }
            else -> {//大于最后一个可见范围
                mRootView.smoothScrollToPosition(position)
                isShouldScroll = true
            }
        }
    }


    override fun setRefreshHeight(scrollValue: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRefreshView(): View? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRefreshing(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}