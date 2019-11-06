package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import androidx.navigation.findNavController
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.UserPreferences
import com.jennifer.andy.simpleeyes.databinding.ActivityLandingBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 09:45
 * Description:加载页，用于区分显示广告，还是视频，还是本地的加载页
 */

class LandingActivity : BaseDataBindActivity<ActivityLandingBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (UserPreferences.getUserIsFirstLogin()) {//如果是第一次进入就加载视频界面
            val action = NavigationEmptyFragmentDirections.actionNavigationEmptyFragmentToVideoLandingFragment()
            findNavController(R.id.nav_host).navigate(action)
        } else {//如果不是加载常规加载界面
            val action = NavigationEmptyFragmentDirections.actionNavigationEmptyFragmentToLocalCommonLandingFragment()
            findNavController(R.id.nav_host).navigate(action)
        }
    }

    override fun getContentViewLayoutId(): Int = R.layout.activity_landing
}