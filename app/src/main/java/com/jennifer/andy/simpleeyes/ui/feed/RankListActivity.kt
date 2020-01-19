package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityRankBinding
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/7/26 18:05
 * Description:排行榜
 */

@Route(path = "/AndyJennifer/ranklist")
class RankListActivity : BaseStateViewActivity<ActivityRankBinding>() {

    private val mRankListViewModel: RankListViewModel by currentScope.viewModel(this)

    @Autowired
    @JvmField
    var tabIndex: String? = null//哇，这里ARouter居然不能支持Int类型

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)

        initToolBar(mDataBinding.toolBar, R.string.open_eyes_english, FontType.LOBSTER)

        mRankListViewModel.getRankListTab()

        mRankListViewModel.observeViewState()
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
                showNetError { mRankListViewModel.getRankListTab() }
            }
        }
    }


    private fun loadTabSuccess(tabInfo: TabInfo) {
        with(mDataBinding) {
            tabLayout.visibility = View.VISIBLE
            viewPager.adapter = FragmentLazyPagerAdapter(supportFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
            viewPager.offscreenPageLimit = tabInfo.tabList.size
            tabIndex?.let { viewPager.currentItem = it.toInt() }
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


    override fun getContentViewLayoutId() = R.layout.activity_rank

}