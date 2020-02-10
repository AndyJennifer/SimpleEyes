package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityTagBinding
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.net.entity.TabDetailInfo
import com.jennifer.andy.simpleeyes.net.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 14:00
 * Description: 360 全景
 */

@Route(path = "/AndyJennifer/tag")
class TagActivity : BaseDataBindActivity<ActivityTagBinding>() {


    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mDataBinding.toolBar, title)
        initTabInfo()
    }

    private fun initTabInfo() {
        /**
         * 这里拼数据，是因为抓包的时候，没有发现获取tab信息的接口，所以自己拼的数据
         */
        val tabInfoDetailList = mutableListOf<TabDetailInfo>().apply {
            add(TabDetailInfo(0, "按时间排序", "${Api.BASE_URL}api/v3/tag/videos?tagId=$id&strategy=date"))
            add(TabDetailInfo(1, "按分享排序", "${Api.BASE_URL}api/v3/tag/videos?tagId=$id&strategy=shareCount"))
        }

        val tabInfo = TabInfo(tabInfoDetailList, 0)

        with(mDataBinding) {
            viewPager.adapter = FragmentLazyPagerAdapter(supportFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
            viewPager.offscreenPageLimit = tabInfo.tabList.size
            mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager)
        }
    }

    private fun initFragments(tabInfo: TabInfo): MutableList<Fragment> {
        val fragments = mutableListOf<Fragment>()
        for (i in tabInfo.tabList.indices) {
            fragments.add(TagDetailInfoFragment.newInstance(tabInfo.tabList[i].apiUrl))
        }
        return fragments
    }

    private fun initTitles(tabInfo: TabInfo): MutableList<String> {
        val titles = mutableListOf<String>()
        for (i in tabInfo.tabList.indices) {
            titles.add(tabInfo.tabList[i].name)
        }
        return titles
    }

    override fun getContentViewLayoutId() = R.layout.activity_tag
}