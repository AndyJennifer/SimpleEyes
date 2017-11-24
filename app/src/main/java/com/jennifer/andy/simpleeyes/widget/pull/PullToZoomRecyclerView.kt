package com.jennifer.andy.simpleeyes.widget.pull

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Author:  andy.xwt
 * Date:    2017/11/24 14:00
 * Description:下拉放大recyclerView,只支持竖直拖动
 */

class PullToZoomRecyclerView : PullToZoomBase<RecyclerView> {


    private lateinit var mHeaderContainer: FrameLayout
    private var mHeaderHeight: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        //添加视差效果
        mRootView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mZoomView != null && !isHideHeader() && isPullToZoomEnabled()) {
                    val dy = mHeaderHeight - mHeaderContainer.bottom
                    if (isParallax()) {
                        if (dy in 1..(mHeaderHeight - 1)) {
                            val parallaxDy = 0.65 * dy
                            mHeaderContainer.scrollTo(0, parallaxDy.toInt())
                        } else if (mHeaderContainer.scrollY != 0) {
                            mHeaderContainer.scrollTo(0, 0)
                        }
                    }
                }
            }
        })
    }

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
        val adapter = mRootView.adapter as BaseQuickAdapter<*, *>
        adapter.addHeaderView(mHeaderContainer)
    }

    override fun createRootView(context: Context, attrs: AttributeSet?): RecyclerView = RecyclerView(context, attrs)

    /**
     * 判断当前recyclerView是否滑动到顶部，如果是在顶部就可以进行下拉
     */
    override fun isReadyForPullStart(): Boolean {
        val adapter = mRootView.adapter
        val layoutManager = mRootView.layoutManager as GridLayoutManager
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
        val lp = mHeaderContainer.layoutParams
        lp.height = Math.abs(scrollValue) + lp.height
        mHeaderContainer.layoutParams = lp
    }

    /**
     * 滑动到顶部
     */
    override fun smoothScrollToTop() {
        //todo 完善
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
    public fun setAdapterAndLayoutManager(adapter: BaseQuickAdapter<*, *>, layoutManager: LinearLayoutManager) {
        mRootView.adapter = adapter
        mRootView.layoutManager = layoutManager

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
            mHeaderHeight = mHeaderContainer.height
            adapter.addHeaderView(mHeaderContainer)
        }
    }

    //todo 添加回退动画
}