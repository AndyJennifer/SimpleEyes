package com.jennifer.andy.simpleeyes.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityMainBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity
import com.jennifer.andy.simpleeyes.widget.BottomBar
import com.jennifer.andy.simpleeyes.widget.BottomItem


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:54
 * Description:主界面
 */


class MainActivity : BaseDataBindActivity<ActivityMainBinding>() {


    companion object {
        private const val HOME_INDEX = 0
        private const val FEED_INDEX = 1
        private const val FOLLOW_INDEX = 2
        private const val PROFILE_INDEX = 3
    }

    private val mTabFragmentsDirection: Map<Int, Int> = mapOf(
            HOME_INDEX to R.id.homeFragment,
            FEED_INDEX to R.id.feedFragment,
            FOLLOW_INDEX to R.id.followFragment,
            PROFILE_INDEX to R.id.profileFragment
    )

    override fun initView(savedInstanceState: Bundle?) {
        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        val home = BottomItem(R.drawable.ic_tab_strip_icon_feed_selected, getString(R.string.home))
        home.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_feed)
        val discover = BottomItem(R.drawable.ic_tab_strip_icon_category_selected, getString(R.string.discover))
        discover.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_category)
        val focus = BottomItem(R.drawable.ic_tab_strip_icon_follow_selected, getString(R.string.focus))
        focus.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_follow)
        val mine = BottomItem(R.drawable.ic_tab_strip_icon_profile_selected, getString(R.string.mine))
        mine.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_profile)

        mDataBinding.bottomNavigationBar
                .addItem(home)
                .addItem(discover)
                .addItem(focus)
                .addItem(mine)
                .initialise()

        mDataBinding.bottomNavigationBar.setOnTabSelectedListener(object : BottomBar.TabSelectedListener {
            override fun onTabSelected(position: Int, prePosition: Int) {
                findNavController(R.id.nav_host).navigate(mTabFragmentsDirection.getValue(position))
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
            }
        })
    }


    override fun getContentViewLayoutId() = R.layout.activity_main

}