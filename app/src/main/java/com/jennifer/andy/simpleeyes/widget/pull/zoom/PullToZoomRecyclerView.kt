package com.jennifer.andy.simpleeyes.widget.pull.zoom

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Author:  andy.xwt
 * Date:    2017/11/24 14:00
 * Description:下拉放大recyclerView,只支持竖直拖动
 */

class PullToZoomRecyclerView : PullToZoomBase<RecyclerView> {


    private lateinit var mHeaderContainer: FrameLayout
    private var mHeaderHeight: Int = 0
    private var mValueAnimator: ValueAnimator? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 添加头布局在recyclerView中，头布局包括 zoomView与headView
     */
    override fun handleStyledAttributes(typedArray: TypedArray) {
        mHeaderContainer = FrameLayout(context)
        mZoomView?.let {
            mHeaderContainer.addView(mZoomView)
        }
        mHeadView?.let {
            mHeaderContainer.addView(mHeadView)
        }
        mRootView.adapter?.let {
            val adapter = mRootView.adapter as BaseQuickAdapter<*, *>
            adapter.addHeaderView(mHeaderContainer)
        }

    }

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
     * 改变头部布局高度
     */
    override fun pullHeadToZoom(scrollValue: Int) {
        mValueAnimator?.let {
            if (mValueAnimator!!.isStarted) {
                mValueAnimator?.cancel()
            }
        }
        val lp = mHeaderContainer.layoutParams
        lp.height = Math.abs(scrollValue) + mHeaderHeight
        mHeaderContainer.layoutParams = lp
    }

    /**
     * 滑动到顶部
     */
    override fun smoothScrollToTop() {
        mValueAnimator = ValueAnimator.ofInt(mHeaderContainer.bottom, mHeaderHeight)
        mValueAnimator?.duration = 350
        mValueAnimator?.interpolator = AccelerateInterpolator()
        mValueAnimator?.addUpdateListener {
            val lp = mHeaderContainer.layoutParams
            lp.height = it.animatedValue as Int
            mHeaderContainer.layoutParams = lp
            if (lp.height == mHeaderHeight) {
                mPullZoomListener?.onPullZoomEnd()
            }
        }
        mValueAnimator?.start()

    }

    /**
     * 设置头布局
     */
    override fun setHeaderView(headerView: View?) {
        headerView?.let {
            mHeadView = headerView
            updateZoomAndHeaderView()
        }
    }


    /**
     * 设置头布局的高度，与宽度，该方法必须要调用
     */
    override fun setHeaderViewLayoutParams(layoutParams: LinearLayout.LayoutParams) {
        mHeaderContainer.let {
            mHeaderContainer.layoutParams = layoutParams
            mHeaderHeight = layoutParams.height
        }
    }

    /**
     * 设置放大布局
     */
    override fun setZoomView(zoomView: View?) {
        zoomView?.let {
            mZoomView = zoomView
            updateZoomAndHeaderView()
        }
    }

    /**
     * 更新放大布局与头布局
     */
    private fun updateZoomAndHeaderView() {
        mHeaderContainer?.let {
            mZoomView?.let {
                mHeaderContainer.addView(mZoomView)
            }
            mHeadView?.let {
                mHeaderContainer.addView(mHeadView)
            }
        }
    }

    /**
     * 设置适配器与布局管理器
     */
    fun setAdapterAndLayoutManager(adapter: BaseQuickAdapter<*, *>, layoutManager: LinearLayoutManager) {
        mRootView.adapter = adapter
        mRootView.layoutManager = layoutManager
        mRootView.itemAnimator = DefaultItemAnimator()
        updateView()
    }

    /**
     * 更新RecyclerView中的布局，并获取头布局的高度
     */
    private fun updateView() {
        mHeaderContainer?.let {
            val adapter = mRootView.adapter as BaseQuickAdapter<*, *>
            adapter.removeHeaderView(mHeaderContainer)
            mHeaderContainer.removeAllViews()
            updateZoomAndHeaderView()
            //获取头布局的高度
            mHeaderHeight = mHeaderContainer.layoutParams.height
            adapter.addHeaderView(mHeaderContainer)
        }
    }

    fun scrollToTop() {
        mRootView.smoothScrollToPosition(0)
    }

}