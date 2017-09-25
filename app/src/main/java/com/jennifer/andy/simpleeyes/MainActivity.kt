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
        val home = BottomNavigationItem(R.drawable.ic_tab_strip_icon_feed_selected, getString(R.string.home))
                .setInactiveIconResource(R.drawable.ic_tab_strip_icon_feed)
        val discover = BottomNavigationItem(R.drawable.ic_tab_strip_icon_follow_selected, getString(R.string.discover))
                .setInactiveIconResource(R.drawable.ic_tab_strip_icon_follow)
        val focus = BottomNavigationItem(R.drawable.ic_tab_strip_icon_category_selected, getString(R.string.focus))
                .setInactiveIconResource(R.drawable.ic_tab_strip_icon_category)
        val mine = BottomNavigationItem(R.drawable.ic_tab_strip_icon_profile_selected, getString(R.string.mine))
                .setInactiveIconResource(R.drawable.ic_tab_strip_icon_profile)
        mBottomNavigation.addItem(home)
        mBottomNavigation.addItem(discover)
        mBottomNavigation.addItem(focus)
        mBottomNavigation.addItem(mine)
        mBottomNavigation.initialise()
        mBottomNavigation.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabUnselected(position: Int) {
                if (position > 1) {
                    //todo 出栈
                }
            }

            override fun onTabReselected(position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTabSelected(position: Int) {
                showHideFragment(mFragments[position])
            }
        })
    }

    override fun getBundleExtras(extras: Bundle) {

    }


    override fun getContentViewLayoutId() = R.layout.activity_main


}