package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:发现
 */

class FeedFragment : BaseFragment<FeedView, FeedPresenter>() {


    companion object {
        fun newInstance(): FeedFragment = FeedFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_feed

    override fun initPresenter() = FeedPresenter(context)
}