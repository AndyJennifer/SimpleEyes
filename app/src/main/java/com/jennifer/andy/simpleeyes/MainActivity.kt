package com.jennifer.andy.simpleeyes

import android.os.Bundle
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.ui.category.CategoryFragment
import com.jennifer.andy.simpleeyes.ui.feed.FeedFragment
import com.jennifer.andy.simpleeyes.ui.follow.FollowFragment
import com.jennifer.andy.simpleeyes.ui.profile.ProfileFragment
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.BottomBar
import com.jennifer.andy.simpleeyes.widget.BottomItem
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:54
 * Description:主界面 
 */

class MainActivity : BaseAppCompatActivity() {

    private var mFragments = arrayOfNulls<SupportFragment>(4)
    private val mBottomNavigation: BottomBar by bindView(R.id.bottom_navigation_bar)

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
        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        var home = BottomItem(R.drawable.ic_tab_strip_icon_feed_selected, getString(R.string.home))
        home.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_feed)
        val discover = BottomItem(R.drawable.ic_tab_strip_icon_follow_selected, getString(R.string.discover))
        discover.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_follow)
        val focus = BottomItem(R.drawable.ic_tab_strip_icon_category_selected, getString(R.string.focus))
        focus.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_category)
        val mine = BottomItem(R.drawable.ic_tab_strip_icon_profile_selected, getString(R.string.mine))
        mine.setUnSelectedDrawable(R.drawable.ic_tab_strip_icon_profile)

        mBottomNavigation.addItem(home)
        mBottomNavigation.addItem(discover)
        mBottomNavigation.addItem(focus)
        mBottomNavigation.addItem(mine)
        mBottomNavigation.initialise()
        mBottomNavigation.setOnTabSelectedListener(object : BottomBar.onTabSelectedListener {
            override fun onTabSelected(position: Int, prePosition: Int) {
                showHideFragment(mFragments[position])
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                //刷新fragment
            }
        })
    }

    override fun getBundleExtras(extras: Bundle) {

    }


    override fun getContentViewLayoutId() = R.layout.activity_main


}