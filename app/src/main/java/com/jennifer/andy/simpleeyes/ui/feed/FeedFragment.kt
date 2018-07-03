package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedView
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:发现
 */

class FeedFragment : BaseFragment<FeedView, FeedPresenter>(), FeedView {

    private val mViewPager: ViewPager by bindView(R.id.view_pager)
    private val mIvSearch: ImageView by bindView(R.id.iv_search)
    private val mTabLayout: TabLayout by bindView(R.id.tab_layout)


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

    override fun loadTabSuccess(it: TabInfo?) {

        //todo 初始化tabLayout,创建fragment。
    }

    override fun initPresenter() = FeedPresenter()

    override fun getContentViewLayoutId() = R.layout.fragment_feed
}