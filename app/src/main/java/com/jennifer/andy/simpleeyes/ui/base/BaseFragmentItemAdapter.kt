package com.jennifer.andy.simpleeyes.ui.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Author:  andy.xwt
 * Date:    2018/7/4 13:59
 * Description:基础FragmentPagerAdapter适配器
 */

open class BaseFragmentItemAdapter(fragmentManager: FragmentManager,
                              private val fragments: MutableList<Fragment>,
                              private val titles: MutableList<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]

}