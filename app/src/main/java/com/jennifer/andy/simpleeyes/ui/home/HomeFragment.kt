package com.jennifer.andy.simpleeyes.ui.home

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.home.presenter.HomePresenter
import com.jennifer.andy.simpleeyes.ui.home.view.HomeView
import com.jennifer.andy.simpleeyes.utils.ScreenUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.pull.head.HomePageHeaderView
import com.jennifer.andy.simpleeyes.widget.pull.zoom.PullToZoomBase
import com.jennifer.andy.simpleeyes.widget.pull.zoom.PullToZoomRecyclerView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:首页
 */

class HomeFragment : BaseFragment<HomeView, HomePresenter>(), HomeView {

    private val mPullToZoomRecycler: PullToZoomRecyclerView by bindView(R.id.rv_home_recycler)
    private lateinit var mHomePageHeaderView: HomePageHeaderView
    private var mCateGoryAdapter: BaseDataAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPullToZoomRecycler.setOnPullZoomListener(object : PullToZoomBase.OnPullZoomListener {
            override fun onPullZooming(scrollValue: Int) {
                mHomePageHeaderView.showRefreshCover(scrollValue)
            }

            override fun onPullZoomEnd() {
                if (mHomePageHeaderView.judgeCanRefresh()) {//只有达到刷新的阀值，上升才刷新，其他情况不刷新
                    mPresenter.refreshCategoryData()
                } else {
                    mHomePageHeaderView.hideRefreshCover()
                }
            }
        })
        mPresenter.loadCategoryData()
    }

    override fun loadDataSuccess(andyInfo: AndyInfo) {
        if (mCateGoryAdapter == null) {
            setHeaderInfo(andyInfo)
            setAdapterAndListener(andyInfo)
        } else {
            mCateGoryAdapter?.setNewData(andyInfo.itemList)
        }
    }

    private fun setAdapterAndListener(andyInfo: AndyInfo) {
        val recyclerView = mPullToZoomRecycler.getPullRootView()
        recyclerView.setItemViewCacheSize(10)
        mCateGoryAdapter = BaseDataAdapter(andyInfo.itemList)
        mCateGoryAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreCategoryData() }, recyclerView)
        mCateGoryAdapter?.setLoadMoreView(CustomLoadMoreView())
        mPullToZoomRecycler.setAdapterAndLayoutManager(mCateGoryAdapter!!, LinearLayoutManager(_mActivity))
    }

    private fun setHeaderInfo(andyInfo: AndyInfo) {
        mHomePageHeaderView = HomePageHeaderView(context!!)
        val lp = ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(context!!), ScreenUtils.getScreenHeight(context!!) / 2)
        mPullToZoomRecycler.setHeaderViewLayoutParams(LinearLayout.LayoutParams(lp))
        mHomePageHeaderView.setHeaderInfo(andyInfo.topIssue, andyInfo.topIssue.data.itemList, this)
        mPullToZoomRecycler.setHeaderView(mHomePageHeaderView)
    }


    override fun refreshDataSuccess(andyInfo: AndyInfo) {
        mCateGoryAdapter?.removeAllFooterView()
        mCateGoryAdapter?.setNewData(andyInfo.itemList)
        mHomePageHeaderView.hideRefreshCover()
    }


    override fun loadMoreSuccess(andyInfo: AndyInfo) {
        mCateGoryAdapter?.addData(andyInfo.itemList)
        mCateGoryAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mCateGoryAdapter?.loadMoreEnd()
    }

    fun scrollToTop() {
        mPullToZoomRecycler.scrollToTop()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_home


}