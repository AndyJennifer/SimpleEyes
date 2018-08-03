package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.UserPreferences
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 09:45
 * Description:加载页，用于区分显示广告，还是视频，还是本地的加载页
 */

class LandingActivity : BaseAppCompatActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        if (UserPreferences.getUserIsFirstLogin()) {//如果是第一次进入就加载视频界面
            loadRootFragment(R.id.fl_container, VideoLandingFragment.newInstance())
        } else {//如果不是加载常规加载界面
            loadRootFragment(R.id.fl_container, LocalCommonLandingFragment.newInstance())
        }
    }

    override fun getContentViewLayoutId(): Int = R.layout.activity_landing
}