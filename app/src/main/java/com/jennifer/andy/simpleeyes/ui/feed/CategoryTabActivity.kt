package com.jennifer.andy.simpleeyes.ui.feed

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter
import com.jennifer.andy.base.utils.showKeyboard
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityCategoryTabBinding
import com.jennifer.andy.simpleeyes.entity.Category
import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.widget.StickyNavLayout
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.FontType
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:46
 * Description:种类界面，包括广告、剧情等同类型的分类。
 */

@Route(path = "/AndyJennifer/category")
class CategoryTabActivity : BaseStateViewActivity<ActivityCategoryTabBinding>() {
    
    private val mCategoryTabViewModel: CategoryTabViewModel by currentScope.viewModel(this)

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null

    @Autowired
    @JvmField
    var tabIndex: String? = null


    override fun initView(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        mCategoryTabViewModel.getCategoryTabIno(id!!)

        initToolBar(R.drawable.ic_action_back_white, title, 0f)

        mCategoryTabViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)
    }

    private fun onNewStateArrive(viewState: ViewState<Category>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                showLoadTabSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mCategoryTabViewModel.getCategoryTabIno(id!!) }
            }
        }
    }


    private fun showLoadTabSuccess(category: Category) {
        with(mDataBinding) {

            ivImage.setImageURI(category.categoryInfo.headerImage)
            tvSubTitle.text = category.categoryInfo.name
            tvDesc.text = category.categoryInfo.description

            idStickyNavLayoutViewpager.adapter = FragmentLazyPagerAdapter(supportFragmentManager, initFragments(category.tabInfo), initTitles(category.tabInfo))
            idStickyNavLayoutNavView.setupWithViewPager(mDataBinding.idStickyNavLayoutViewpager)
            idStickyNavLayoutViewpager.currentItem = tabIndex?.toInt() ?: 0

            stickLayout.setScrollChangeListener(object : StickyNavLayout.ScrollChangeListener {
                override fun onScroll(moveRatio: Float) {
                    if (moveRatio < 1) initToolBar(R.drawable.ic_action_back_white, title, moveRatio)
                    else initToolBar(R.drawable.ic_action_back_black, title, moveRatio)
                }
            })
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


    private fun initToolBar(@DrawableRes backResId: Int, title: String? = null, titleAlpha: Float = 1f) {
        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            showKeyboard(false)
            finish()
        }
        //设置渐变
        val color = ArgbEvaluator().evaluate(titleAlpha, Color.WHITE, Color.BLACK) as Int
        val wrapDrawable = DrawableCompat.wrap(getDrawable(backResId))
        wrapDrawable.setTint(color)
        ivBack.setImageDrawable(wrapDrawable)

        val tvTitle = findViewById<CustomFontTextView>(R.id.tv_title)
        tvTitle.setFontType(fontType = FontType.BOLD)
        tvTitle.text = title
        tvTitle.alpha = titleAlpha

    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_category_tab

}