package com.jennifer.andy.simpleeyes.ui.author

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.RelativeLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.author.presenter.AuthorTagDetailPresenter
import com.jennifer.andy.simpleeyes.ui.author.ui.AuthorTagDetailView
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseFragmentItemAdapter
import com.jennifer.andy.simpleeyes.ui.feed.TagDetailInfoFragment
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.StickyNavLayout
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:38
 * Description: 作者详细信息界面
 */

class AuthorTagDetailActivity : BaseActivity<AuthorTagDetailView, AuthorTagDetailPresenter>(), AuthorTagDetailView {

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mStickyNavLayout: StickyNavLayout by bindView(R.id.stick_layout)
    private val mViewPager: ViewPager by bindView(R.id.id_sticky_nav_layout_viewpager)
    private val mTabLayout: ShortTabLayout by bindView(R.id.id_sticky_nav_layout_nav_view)


    private lateinit var id: String


    companion object {
        /**
         * 跳转道作者详细界面，id:作者id
         */
        @JvmStatic
        fun start(context: Context, id: String) {
            val bundle = Bundle()
            bundle.putString(Extras.ID, id)
            val starter = Intent(context, AuthorTagDetailActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)

        }
    }

    override fun getBundleExtras(extras: Bundle) {
        id = extras.getString(Extras.ID)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getAuthorTagDetail(id)
    }


    override fun loadInfoSuccess(it: Tab) {

        initToolBar(mToolbar, it.pgcInfo.name)
        mViewPager.adapter = BaseFragmentItemAdapter(supportFragmentManager, initFragments(it.tabInfo), initTitles(it.tabInfo))
        mTabLayout.setupWithViewPager(mViewPager)
        mStickyNavLayout.setScrollChangeListener(object : StickyNavLayout.ScrollChangeListener {
            override fun onScroll(moveRatio: Float) {

            }
        })

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

    override fun getContentViewLayoutId() = R.layout.activity_author_tag_detail
}