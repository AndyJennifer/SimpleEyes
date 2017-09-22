package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.presenter.FollowPresenter

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:
 */

class FollowFragment : BaseFragment<FollowPresenter, FollowModel>() {


    companion object {
        fun newInstance(): FollowFragment = FollowFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {

    }

}