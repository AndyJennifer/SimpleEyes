package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.category.adapter.CategoryAdapter
import com.jennifer.andy.simpleeyes.ui.category.presenter.CategoryPresenter
import com.jennifer.andy.simpleeyes.ui.category.view.CategoryView
import com.jennifer.andy.simpleeyes.utils.ScreenUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.HomePageHeaderView
import com.jennifer.andy.simpleeyes.widget.pull.PullToZoomBase
import com.jennifer.andy.simpleeyes.widget.pull.PullToZoomRecyclerView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:首页
 */

class CategoryFragment : BaseFragment<CategoryView, CategoryPresenter>(), CategoryView {

    private val mPullToZoomRecycler: PullToZoomRecyclerView by bindView(R.id.rv_recycler)
    private lateinit var mHomePageHeaderView: HomePageHeaderView
    private var mCateGoryAdapter: CategoryAdapter? = null

    companion object {
        fun newInstance(): CategoryFragment = CategoryFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.loadCategoryData()
        mPullToZoomRecycler.setOnPullZoomListener(object : PullToZoomBase.onPullZoomListener {
            override fun onPullZooming(scrollValue: Int) {
                mHomePageHeaderView.showRefreshCover(scrollValue)
            }

            override fun onPullZoomEnd() {
                mHomePageHeaderView.hideRefreshCover()
            }
        })
    }

    override fun loadDataSuccess(andyInfo: AndyInfo) {
        if (mCateGoryAdapter == null) {

            mCateGoryAdapter = CategoryAdapter(andyInfo.itemList)
            mCateGoryAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                //跳转到详情界面
                val item = adapter.getItem(position) as AndyInfo
                startBannerDetailActivity()
            }

            addCategoryHeader(andyInfo)
            setVideoInfo()

        } else {
            mCateGoryAdapter?.setNewData(andyInfo.itemList)
        }
    }

    /**
     * 添加头布局
     */
    private fun addCategoryHeader(andyInfo: AndyInfo) {
        mHomePageHeaderView = HomePageHeaderView(context)
        mHomePageHeaderView.setHeaderInfo(andyInfo.topIssue)
        mPullToZoomRecycler.setHeaderView(mHomePageHeaderView)
        val lp = ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context) / 2)
        mPullToZoomRecycler.setHeaderViewLayoutParams(LinearLayout.LayoutParams(lp))
    }

    /**
     * 设置视频信息
     */
    private fun setVideoInfo() {
        val linearLayoutManager = LinearLayoutManager(_mActivity)
        val recyclerView = mPullToZoomRecycler.getPullRootView()
        recyclerView.setItemViewCacheSize(10)
        mCateGoryAdapter?.bindToRecyclerView(recyclerView)
        mPullToZoomRecycler.setAdapterAndLayoutManager(mCateGoryAdapter!!, linearLayoutManager)
    }

    /**
     * 跳转到视频banner详情界面
     */
    private fun startBannerDetailActivity() {

    }

    /**
     * 跳转到视频followCard详情界面
     */
    private fun startFollowCardDetailActivity() {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_category

    override fun initPresenter() = CategoryPresenter(context)


}