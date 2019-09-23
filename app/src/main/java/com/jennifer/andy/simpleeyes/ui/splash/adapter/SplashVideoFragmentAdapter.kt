package com.jennifer.andy.simpleeyes.ui.splash.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jennifer.andy.simpleeyes.ui.splash.SloganFragment


/**
 * Author:  andy.xwt
 * Date:    2018/5/15 10:59
 * Description: 闪屏视频适配器
 */

class SplashVideoFragmentAdapter(var mFragmentList: MutableList<SloganFragment>,
                                 fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int) = mFragmentList[position]


    override fun getCount() = mFragmentList.size


}