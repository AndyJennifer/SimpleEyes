package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.follow.presenter.FollowPresenter
import com.jennifer.andy.simpleeyes.ui.follow.view.FollowView

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description: 关注
 */

class FollowFragment : BaseFragment<FollowView, FollowPresenter>() {


    companion object {
        fun newInstance(): FollowFragment = FollowFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_follow

    override fun initPresenter() = FollowPresenter()
}
