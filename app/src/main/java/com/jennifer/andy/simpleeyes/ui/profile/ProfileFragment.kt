package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.profile.model.ProfileModel
import com.jennifer.andy.simpleeyes.ui.profile.presenter.ProfilePresenter

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:
 */

class ProfileFragment : BaseFragment<ProfilePresenter, ProfileModel>() {


    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {

    }

}