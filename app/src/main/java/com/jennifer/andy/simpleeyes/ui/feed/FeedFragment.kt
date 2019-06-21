package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.base.BaseFragmentItemAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedView
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:发现
 */

class FeedFragment : BaseFragment<FeedView, FeedPresenter>(), FeedView {

    private val mViewPager: ViewPager by bindView(R.id.view_pager)
    private val mTvAllCategory: CustomFontTextView by bindView(R.id.tv_all_category)
    private val mIvSearch: ImageView by bindView(R.id.iv_search)
    private val mTabLayout: ShortTabLayout by bindView(R.id.tab_layout)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)

    companion object {
        @JvmStatic
        fun newInstance(): FeedFragment = FeedFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getDiscoveryTab()

        //跳转到搜索界面
        mIvSearch.setOnClickListener {
            readyGo(SearchHotActivity::class.java, null)
        }
        //跳转到全部分类界面
        mTvAllCategory.setOnClickListener {
            readyGo(AllCategoryActivity::class.java)
        }
    }

    override fun loadTabSuccess(tabInfo: TabInfo) {
        mViewPager.adapter = BaseFragmentItemAdapter(childFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
        mViewPager.offscreenPageLimit = tabInfo.tabList.size
        mTabLayout.setupWithViewPager(mViewPager)
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

    override fun showNetError(onClickListener: View.OnClickListener) {
        mStateView.showNetError(onClickListener)
    }

    override fun showContent() {
        mStateView.showContent()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_feed
}