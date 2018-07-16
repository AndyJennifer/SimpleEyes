package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.Toolbar
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.feed.presenter.AllCategoryPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.AllCategoryView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/7/16 14:17
 * Description: 全部分类
 */

class AllCategoryActivity : BaseActivity<AllCategoryView, AllCategoryPresenter>() {

    private val mToolBar: Toolbar by bindView(R.id.tool_bar)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)

    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mToolBar, getString(R.string.all_category))
    }


    override fun initPresenter() = AllCategoryPresenter()

    override fun getContentViewLayoutId() = R.layout.activity_all_category
}