package com.jennifer.andy.simpleeyes.ui.author

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter
import com.jennifer.andy.base.utils.showKeyboard
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityAuthorTagDetailBinding
import com.jennifer.andy.simpleeyes.net.entity.Tab
import com.jennifer.andy.simpleeyes.net.entity.TabInfo
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.feed.TagDetailInfoFragment
import com.jennifer.andy.simpleeyes.widget.StickyNavLayout
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import kotlinx.android.synthetic.main.fragment_local_coomon_landing.view.tv_name
import kotlinx.android.synthetic.main.layout_author_tag_detail_header.view.*
import kotlinx.android.synthetic.main.layout_common_text.view.*
import kotlinx.android.synthetic.main.layout_common_text.view.tv_desc
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:38
 * Description: 作者详细信息界面
 */

@Route(path = "/pgc/detail")
class AuthorTagDetailActivity : BaseStateViewActivity<ActivityAuthorTagDetailBinding>() {

    private val mAuthorTagViewModel: AuthorTagViewModel by currentScope.viewModel(this)

    @Autowired
    @JvmField
    var tabIndex: String? = null

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null


    override fun getBundleExtras(extras: Bundle?) {
        extras?.let {
            tabIndex = it.getString(Extras.TAB_INDEX)
            title = it.getString(Extras.TITLE)
            id = it.getString(Extras.ID)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        initToolBar(title, 0f)

        mAuthorTagViewModel.getAuthorTagDetail(id!!)

        mAuthorTagViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)

    }


    private fun onNewStateArrive(viewState: ViewState<Tab>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                loadInfoSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mAuthorTagViewModel.getAuthorTagDetail(id!!) }
            }
        }
    }


    private fun loadInfoSuccess(tab: Tab) {

        with(mDataBinding) {
            idStickyNavLayoutViewpager.adapter = FragmentLazyPagerAdapter(supportFragmentManager, initFragments(tab.tabInfo), initTitles(tab.tabInfo))
            idStickyNavLayoutNavView.setupWithViewPager(idStickyNavLayoutViewpager)
            idStickyNavLayoutViewpager.currentItem = tabIndex?.toInt() ?: 0
            stickLayout.setScrollChangeListener(object : StickyNavLayout.ScrollChangeListener {
                override fun onScroll(moveRatio: Float) {
                    initToolBar(title, moveRatio)
                }
            })
        }

        //设置topView信息
        with(tab.pgcInfo) {
            with(mDataBinding.idStickyNavLayoutTopView) {
                tv_name.text = name
                tv_desc.text = description
                tv_brief.text = brief
                iv_head.setImageURI(icon)
            }
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
        mDataBinding.toolBar.setBackgroundColor(color)

        mDataBinding.toolBar.tv_title.apply {
            setFontType(fontType = FontType.BOLD)
            text = title
            alpha = titleAlpha
        }

    }


    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_author_tag_detail
}