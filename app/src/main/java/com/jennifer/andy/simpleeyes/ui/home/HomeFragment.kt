package com.jennifer.andy.simpleeyes.ui.home

import android.os.Bundle
import com.jennifer.andy.simpleeyes.ui.home.model.HomeModel
import com.jennifer.andy.simpleeyes.ui.home.presenter.HomePresenter
import com.jennifer.andy.simplemusic.R
import com.jennifer.andy.simplemusic.ui.BaseFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:
 */

class HomeFragment : BaseFragment<HomePresenter, HomeModel>() {


    override fun getBundleExtras(extras: Bundle) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {

    }

}