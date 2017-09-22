package com.jennifer.andy.simpleeyes

import android.os.Bundle
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.ui.category.CategoryFragment
import com.jennifer.andy.simpleeyes.ui.feed.FeedFragment
import com.jennifer.andy.simpleeyes.ui.follow.FollowFragment
import com.jennifer.andy.simpleeyes.ui.profile.ProfileFragment
import me.yokeyword.fragmentation.SupportFragment


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:54
 * Description:
 */

class MainActivity : BaseAppCompatActivity() {

    private var mFragments = arrayOfNulls<SupportFragment>(4)

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
    }

    override fun getBundleExtras(extras: Bundle) {

    }





    override fun getContentViewLayoutId() = R.layout.activity_main


}