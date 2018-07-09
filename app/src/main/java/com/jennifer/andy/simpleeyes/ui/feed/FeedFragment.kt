package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.feed.adapter.FeedFragmentAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedView
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:发现
 */

class FeedFragment : BaseFragment<FeedView, FeedPresenter>(), FeedView {

    private val mViewPager: ViewPager by bindView(R.id.view_pager)
    private val mIvSearch: ImageView by bindView(R.id.iv_search)
    private val mTabLayout: ShortTabLayout by bindView(R.id.tab_layout)

    companion object {
        fun newInstance(): FeedFragment = FeedFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getDiscoveryTab()

        //跳转到搜索界面
        mIvSearch.setOnClickListener {
            readyGo(SearchHotActivity::class.java, null)
        }
    }

    override fun loadTabSuccess(tabInfo: TabInfo) {
        mViewPager.offscreenPageLimit = tabInfo.tabList.size
        mViewPager.adapter = FeedFragmentAdapter(fragmentManager!!, initFragments(tabInfo), initTitles(tabInfo))
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun initFragments(tabInfo: TabInfo): MutableList<Fragment> {
        val fragments = mutableListOf<Fragment>()
        for (i in tabInfo.tabList.indices) {
            fragments.add(FeedDetailFragment.newInstance(tabInfo.tabList[i].apiUrl))
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

    override fun initPresenter() = FeedPresenter()

    override fun getContentViewLayoutId() = R.layout.fragment_feed
}