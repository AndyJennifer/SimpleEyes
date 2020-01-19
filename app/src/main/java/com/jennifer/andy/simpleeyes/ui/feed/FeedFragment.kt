package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter
import com.jennifer.andy.base.utils.readyGo
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentFeedBinding
import com.jennifer.andy.simpleeyes.net.entity.Tab
import com.jennifer.andy.simpleeyes.net.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewFragment
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:发现
 */

class FeedFragment : BaseStateViewFragment<FragmentFeedBinding>() {

    private val mFeedViewModel: FeedViewModel by currentScope.viewModel(this)

    companion object {
        fun newInstance() = FeedFragment()
    }


    override fun initViewOnCreated(savedInstanceState: Bundle?) {

        //跳转到搜索界面
        mDataBinding.ivSearch.setOnClickListener {
            readyGo<SearchHotActivity>()
        }
        //跳转到全部分类界面
        mDataBinding.tvAllCategory.setOnClickListener {
            readyGo<AllCategoryActivity>()
        }
        mFeedViewModel.getDiscoveryTab()
        mFeedViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)
    }


    private fun onNewStateArrive(viewState: ViewState<Tab>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                loadTabSuccess(viewState.data!!.tabInfo)
            }
            Action.INIT_FAIL -> {
                showNetError { mFeedViewModel.getDiscoveryTab() }
            }
        }
    }

    private fun loadTabSuccess(tabInfo: TabInfo) {
        with(mDataBinding) {
            viewPager.adapter = FragmentLazyPagerAdapter(childFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
            viewPager.offscreenPageLimit = tabInfo.tabList.size
            tabLayout.setupWithViewPager(viewPager)
        }

    }

    private fun initFragments(tabInfo: TabInfo): MutableList<Fragment> {
        val fragments = mutableListOf<Fragment>()
        for (i in tabInfo.tabList.indices) {
            fragments.add(TagDetailInfoFragment.newInstance(tabInfo.tabList[i].apiUrl))
        }
        return fragments
    }

    private fun initTitles(tabInfo: TabInfo): MutableList<String> {
        val titles = mutableListOf<String>()
        for (i in tabInfo.tabList.indices) {
            titles.add(tabInfo.tabList[i].name)
        }
        return titles
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.fragment_feed
}