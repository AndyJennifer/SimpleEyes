package com.jennifer.andy.simpleeyes

import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.ui.category.CategoryFragment
import com.jennifer.andy.simpleeyes.ui.feed.FeedFragment
import com.jennifer.andy.simpleeyes.ui.follow.FollowFragment
import com.jennifer.andy.simpleeyes.ui.profile.ProfileFragment
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:54
 * Description:主界面
 */

class MainActivity : BaseAppCompatActivity() {

    private var mFragments = arrayOfNulls<SupportFragment>(4)
    private val mBottomNavigation: BottomNavigationBar by bindView(R.id.bottom_navigation_bar)

    companion object {
        val FIRST = 0
        val SECOND = 1
        val THIRD = 2
        val FOURTH = 3
    }

    override fun initView(savedInstanceState: Bundle?) {
        mFragments[FIRST] = CategoryFragment.newInstance()
        mFragments[SECOND] = FeedFragment.newInstance()
        mFragments[THIRD] = FollowFragment.newInstance()
        mFragments[FOURTH] = ProfileFragment.newInstance()
        loadMultipleRootFragment(R.id.fl_container, FIRST, *mFragments)
        //初始化
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val home = BottomNavigationItem(R.drawable.ic_tab_strip_icon_category, getString(R.string.home))
        val discover = BottomNavigationItem(R.drawable.ic_tab_strip_icon_feed, getString(R.string.discover))
        val focus = BottomNavigationItem(R.drawable.ic_tab_strip_icon_feed, getString(R.string.focus))
        val mine = BottomNavigationItem(R.drawable.ic_tab_strip_icon_feed, getString(R.string.mine))
        home.setInactiveIconResource(R.drawable.ic_tab_strip_icon_category_selected)
        discover.setInactiveIconResource(R.drawable.ic_tab_strip_icon_feed_selected)
        focus.setInactiveIconResource(R.drawable.ic_tab_strip_icon_follow_selected)
        mine.setInactiveIconResource(R.drawable.ic_tab_strip_icon_profile_selected)

        mBottomNavigation.addItem(home)
    }

    override fun getBundleExtras(extras: Bundle) {

    }


    override fun getContentViewLayoutId() = R.layout.activity_main


}