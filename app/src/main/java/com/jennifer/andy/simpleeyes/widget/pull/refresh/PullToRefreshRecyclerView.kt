package com.jennifer.andy.simpleeyes.widget.pull.refresh

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.widget.pull.head.EliteHeaderView


/**
 * Author:  andy.xwt
 * Date:    2018/6/19 14:37
 * Description:下拉刷新RecyclerView
 */

class PullToRefreshRecyclerView : PullToRefreshBase<RecyclerView> {


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
     * 滚动到相应位置
     */
    fun smoothScrollToPosition(position: Int) {
        mRootView.smoothScrollToPosition(position)
    }

    /**
     * 添加滑动监听
     */
    fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        mRootView.addOnScrollListener(onScrollListener)
    }

    override fun initRefreshView() = EliteHeaderView(context)


    override fun dispatchExtraPullEvent(dy: Float) {
        mRefreshView?.handleExtraPullEvent(dy)
    }

    override fun dispatchValidPullEvent(dy: Float) {
        mRefreshView?.handleValidPullEvent(dy)
    }





}