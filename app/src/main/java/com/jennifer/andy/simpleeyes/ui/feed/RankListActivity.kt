package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.feed.adapter.FeedFragmentAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.RankListPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.RankListView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2018/7/26 18:05
 * Description:排行榜
 */

@Route(path = "/AndyJennifer/ranklist")
class RankListActivity : BaseActivity<RankListView, RankListPresenter>(), RankListView {

    private val mToolbar by bindView<Toolbar>(R.id.tool_bar)
    private val mViewPager: ViewPager by bindView(R.id.view_pager)
    private val mTabLayout: ShortTabLayout by bindView(R.id.tab_layout)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)

    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mToolbar, R.string.open_eyes_english, FontType.LOBSTER)
        mPresenter.getRankListTab()
    }

    override fun loadTabSuccess(tabInfo: TabInfo) {
        mTabLayout.visibility = View.VISIBLE
        mViewPager.adapter = FeedFragmentAdapter(supportFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
        mViewPager.offscreenPageLimit = tabInfo.tabList.size
        mTabLayout.setupWithViewPager(mViewPager)
    }


    private fun initFragments(tabInfo: TabInfo): MutableList<Fragment> {
        val fragments = mutableListOf<Fragment>()
        for (i in tabInfo.tabList.indices) {
            fragments.add(TagDetailInfoInfoFragment.newInstance(tabInfo.tabList[i].apiUrl))
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

    override fun showNetError(onClickListener: View.OnClickListener) {
        mStateView.showNetError(onClickListener)
    }

    override fun showContent() {
        mStateView.showContent()
    }

    override fun getContentViewLayoutId() = R.layout.activity_rank

}