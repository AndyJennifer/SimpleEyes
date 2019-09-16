package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.TabDetailInfo
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseFragmentItemAdapter
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 14:00
 * Description: 360 全景
 */

@Route(path = "/AndyJennifer/tag")
class TagActivity : BaseAppCompatActivity() {

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mTabLayout: ShortTabLayout by bindView(R.id.tab_layout)
    private val mViewPager: ViewPager by bindView(R.id.view_pager)


    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mToolbar, title)
        initTabInfo()
    }

    private fun initTabInfo() {
        /**
         * 这里拼数据，是因为抓包的时候，没有发现获取tab信息的接口，所以自己拼的数据
         */
        val tabInfoDetailList = mutableListOf<TabDetailInfo>()
        tabInfoDetailList.add(TabDetailInfo(0, "按时间排序", "${Api.BASE_URL}api/v3/tag/videos?tagId=$id&strategy=date"))
        tabInfoDetailList.add(TabDetailInfo(1, "按分享排序", "${Api.BASE_URL}api/v3/tag/videos?tagId=$id&strategy=shareCount"))
        val tabInfo = TabInfo(tabInfoDetailList, 0)

        mViewPager.adapter = BaseFragmentItemAdapter(supportFragmentManager, initFragments(tabInfo), initTitles(tabInfo))
        mViewPager.offscreenPageLimit = tabInfo.tabList.size
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun initFragments(tabInfo: TabInfo): MutableList<androidx.fragment.app.Fragment> {
        val fragments = mutableListOf<androidx.fragment.app.Fragment>()
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