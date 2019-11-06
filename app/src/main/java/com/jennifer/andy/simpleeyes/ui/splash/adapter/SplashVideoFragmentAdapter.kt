package com.jennifer.andy.simpleeyes.ui.splash.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jennifer.andy.simpleeyes.ui.splash.SloganFragment


/**
 * Author:  andy.xwt
 * Date:    2018/5/15 10:59
 * Description: 闪屏视频适配器
 */

const val DEFAULT_SPLASH_VIDEO_COUNT = 4

class SplashVideoFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val mFragmentList = List(DEFAULT_SPLASH_VIDEO_COUNT) { SloganFragment.newInstance() } as MutableList<SloganFragment>

    override fun getItem(position: Int) = mFragmentList[position]


    override fun getCount() = mFragmentList.size


}