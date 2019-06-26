package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Category
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseFragmentItemAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.CategoryTabPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.CategoryTabView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:46
 * Description:种类界面，包括广告、剧情等同类型的分类。
 */

@Route(path = "/AndyJennifer/category")
class CategoryTabActivity : BaseActivity<CategoryTabView, CategoryTabPresenter>(), CategoryTabView {

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mViewPager: ViewPager by bindView(R.id.id_sticky_nav_layout_viewpager)
    private val mTabLayout: ShortTabLayout by bindView(R.id.id_sticky_nav_layout_nav_view)

    private val mImageView: SimpleDraweeView by bindView(R.id.iv_image)
    private val mTvSubTitle: CustomFontTextView by bindView(R.id.tv_sub_title)
    private val mTvDesc: CustomFontTextView by bindView(R.id.tv_desc)
    private val mTvFollow: CustomFontTextView by bindView(R.id.tv_follow)

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        mPresenter.getTabInfo(id!!)
        initToolBar(mToolbar, "")
    }

    override fun showLoadTabSuccess(category: Category) {
        mImageView.setImageURI(category.categoryInfo.headerImage)
        mTvSubTitle.text = category.categoryInfo.name
        mTvDesc.text = category.categoryInfo.description

        mViewPager.adapter = BaseFragmentItemAdapter(supportFragmentManager, initFragments(category.tabInfo), initTitles(category.tabInfo))
        mViewPager.offscreenPageLimit = category.tabInfo.tabList.size
        mTabLayout.setupWithViewPager(mViewPager)
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


    override fun getContentViewLayoutId() = R.layout.activity_category_tab

}