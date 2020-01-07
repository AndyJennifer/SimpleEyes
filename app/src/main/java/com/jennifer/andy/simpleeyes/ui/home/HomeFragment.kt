package com.jennifer.andy.simpleeyes.ui.home

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennifer.andy.base.utils.getScreenHeight
import com.jennifer.andy.base.utils.getScreenWidth
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentHomeBinding
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewFragment
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.pull.head.HomePageHeaderView
import com.jennifer.andy.simpleeyes.widget.pull.zoom.PullToZoomBase
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:首页
 */

class HomeFragment : BaseStateViewFragment<FragmentHomeBinding>() {


    private lateinit var mHomePageHeaderView: HomePageHeaderView
    private var mCateGoryAdapter: BaseDataAdapter? = null
    private val mHomeViewModel: HomeViewModel by currentScope.viewModel(this)

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initViewOnCreated(savedInstanceState: Bundle?) {
        mDataBinding.rvHomeRecycler.setOnPullZoomListener(object : PullToZoomBase.OnPullZoomListener {
            override fun onPullZooming(scrollValue: Int) {
                mHomePageHeaderView.showRefreshCover(scrollValue)
            }

            override fun onPullZoomEnd() {
                if (mHomePageHeaderView.judgeCanRefresh()) {//只有达到刷新的阀值，上升才刷新，其他情况不刷新
                    mHomeViewModel.refreshCategoryData()
                } else {
                    mHomePageHeaderView.hideRefreshCover()
                }
            }
        })

        mHomeViewModel.loadCategoryData()//加载主页信息

        mHomeViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)
    }

    private fun onNewStateArrive(viewState: ViewState<AndyInfo>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                loadDataSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mHomeViewModel.loadCategoryData() }
            }
            Action.REFRESH_SUCCESS -> {
                showContent()
                refreshDataSuccess(viewState.data!!)
            }
            Action.REFRESH_FAIL -> {
                showNetError { mHomeViewModel.refreshCategoryData() }
            }
            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mHomeViewModel.loadMoreAndyInfo() }
            }
        }
    }

    private fun loadDataSuccess(data: AndyInfo) {
        if (mCateGoryAdapter == null) {
            setHeaderInfo(data)
            setAdapterAndListener(data)
        } else {
            mCateGoryAdapter?.setNewData(data.itemList)
        }
    }


    private fun setHeaderInfo(data: AndyInfo) {
        mHomePageHeaderView = HomePageHeaderView(requireContext())
        val lp = ViewGroup.LayoutParams(getScreenWidth(), getScreenHeight() / 2)
        mDataBinding.rvHomeRecycler.setHeaderViewLayoutParams(LinearLayout.LayoutParams(lp))
        mHomePageHeaderView.setHeaderInfo(data.topIssue, data.topIssue.data.itemList, this)
        mDataBinding.rvHomeRecycler.setHeaderView(mHomePageHeaderView)
    }


    private fun setAdapterAndListener(data: AndyInfo) {
        val recyclerView = mDataBinding.rvHomeRecycler.getPullRootView()
        recyclerView.setItemViewCacheSize(10)
        mCateGoryAdapter = BaseDataAdapter(data.itemList)
        mCateGoryAdapter?.setOnLoadMoreListener({ mHomeViewModel.loadMoreAndyInfo() }, recyclerView)
        mCateGoryAdapter?.setLoadMoreView(CustomLoadMoreView())
        mDataBinding.rvHomeRecycler.setAdapterAndLayoutManager(mCateGoryAdapter!!, LinearLayoutManager(requireContext()))
    }

    private fun refreshDataSuccess(data: AndyInfo) {
        mCateGoryAdapter?.removeAllFooterView()
        mCateGoryAdapter?.setNewData(data.itemList)
        mHomePageHeaderView.hideRefreshCover()
    }


    private fun loadMoreSuccess(data: AndyInfo) {
        mCateGoryAdapter?.addData(data.itemList)
        mCateGoryAdapter?.loadMoreComplete()
    }

    private fun showNoMore() {
        mCateGoryAdapter?.loadMoreEnd()
    }


    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.fragment_home

}