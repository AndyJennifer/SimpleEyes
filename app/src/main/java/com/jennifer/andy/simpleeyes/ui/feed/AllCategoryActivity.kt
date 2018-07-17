package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toolbar
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter.Companion.RECTANGLE_CARD_TYPE
import com.jennifer.andy.simpleeyes.ui.feed.presenter.AllCategoryPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.AllCategoryView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/7/16 14:17
 * Description: 全部分类
 */

class AllCategoryActivity : BaseActivity<AllCategoryView, AllCategoryPresenter>(), AllCategoryView {

    private val mToolBar: Toolbar by bindView(R.id.tool_bar)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)

    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mToolBar, getString(R.string.all_category))
        mPresenter.loadAllCategoriesInfo()
    }

    override fun loadAllCategoriesSuccess(andyInfo: AndyInfo) {
        val adapter = BaseDataAdapter(andyInfo.itemList)
        adapter.setSpanSizeLookup { _, position ->
            if (adapter.getItemViewType(position) == RECTANGLE_CARD_TYPE) 2 else 1
        }
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        mRecyclerView.adapter = adapter
    }

    override fun initPresenter() = AllCategoryPresenter()

    override fun getContentViewLayoutId() = R.layout.activity_all_category
}