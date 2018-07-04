package com.jennifer.andy.simpleeyes.ui.feed.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.jennifer.andy.simpleeyes.ui.base.BaseFragmentItemAdapter


/**
 * Author:  andy.xwt
 * Date:    2018/7/4 14:03
 * Description:
 */

class FeedFragmentAdapter(fragmentManager: FragmentManager,
                          fragments: MutableList<Fragment>,
                          titles: MutableList<String>) : BaseFragmentItemAdapter(fragmentManager, fragments, titles)

