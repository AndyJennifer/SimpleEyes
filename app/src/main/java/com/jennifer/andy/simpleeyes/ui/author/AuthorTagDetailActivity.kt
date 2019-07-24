package com.jennifer.andy.simpleeyes.ui.author

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
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
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.tab.ShortTabLayout


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:38
 * Description: 作者详细信息界面
 */

@Route(path = "/pgc/detail")
class AuthorTagDetailActivity : BaseActivity<AuthorTagDetailView, AuthorTagDetailPresenter>(), AuthorTagDetailView {

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mTvTitle: CustomFontTextView by bindView(R.id.tv_title)
    private val mStickyNavLayout: StickyNavLayout by bindView(R.id.stick_layout)
    private val mViewPager: ViewPager by bindView(R.id.id_sticky_nav_layout_viewpager)
    private val mTabLayout: ShortTabLayout by bindView(R.id.id_sticky_nav_layout_nav_view)

    private val mTvName: CustomFontTextView by bindView(R.id.tv_name)
    private val mTvDesc: CustomFontTextView by bindView(R.id.tv_desc)
    private val mTvBrief: CustomFontTextView by bindView(R.id.tv_brief)
    private val mIvHead: SimpleDraweeView by bindView(R.id.iv_head)

    @Autowired
    @JvmField
    var tabIndex: String? = null

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null


    override fun getBundleExtras(extras: Bundle) {
        with(extras) {
            tabIndex = getString(Extras.TAB_INDEX)
            title = getString(Extras.TITLE)
            id = getString(Extras.ID)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(title, 0f)
        mPresenter.getAuthorTagDetail(id!!)

    }


    override fun loadInfoSuccess(tab: Tab) {

        mViewPager.adapter = BaseFragmentItemAdapter(supportFragmentManager, initFragments(tab.tabInfo), initTitles(tab.tabInfo))
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = tabIndex?.toInt() ?: 0

        mStickyNavLayout.setScrollChangeListener(object : StickyNavLayout.ScrollChangeListener {
            override fun onScroll(moveRatio: Float) {
                initToolBar(title, moveRatio)
            }
        })

        //设置topView信息
        with(tab.pgcInfo) {
            mTvName.text = name
            mTvDesc.text = description
            mTvBrief.text = brief
            mIvHead.setImageURI(icon)
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

    private fun initToolBar(title: String? = null, titleAlpha: Float = 1f) {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            showKeyboard(false)
            finish()
        }
        //设置背景渐变
        val color = ArgbEvaluator().evaluate(titleAlpha, 0x00FFFFFF, Color.WHITE) as Int
        mToolbar.setBackgroundColor(color)

        mTvTitle.apply {
            setFontType(fontType = FontType.BOLD)
            text = title
            alpha = titleAlpha
        }

    }

    override fun getContentViewLayoutId() = R.layout.activity_author_tag_detail
}