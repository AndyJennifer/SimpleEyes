package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.profile.presenter.ProfilePresenter
import com.jennifer.andy.simpleeyes.ui.profile.view.ProfileView

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:我的
 */

class ProfileFragment : BaseFragment<ProfileView, ProfilePresenter>() {


    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_profile

    override fun initPresenter() = ProfilePresenter(context)


}