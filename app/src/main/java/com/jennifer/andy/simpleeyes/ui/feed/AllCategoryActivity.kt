package com.jennifer.andy.simpleeyes.ui.feed

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter.Companion.RECTANGLE_CARD_TYPE
import com.jennifer.andy.simpleeyes.ui.feed.presenter.AllCategoryPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.AllCategoryView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.GridItemDecoration
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2018/7/16 14:17
 * Description: 全部分类
 */

class AllCategoryActivity : BaseActivity<AllCategoryView, AllCategoryPresenter>(), AllCategoryView {

    private val mToolBar: RelativeLayout by bindView(R.id.tool_bar)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)


    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mToolBar, R.string.all_category)
        mPresenter.loadAllCategoriesInfo()
    }

    override fun loadAllCategoriesSuccess(andyInfo: AndyInfo) {
        val adapter = BaseDataAdapter(andyInfo.itemList)
        adapter.setSpanSizeLookup { _, position ->
            if (adapter.getItemViewType(position) == RECTANGLE_CARD_TYPE) 2 else 1
        }
        //根据actionUrl跳转到相应界面
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            ARouter.getInstance()
                    .build(Uri.parse(adapter.data[position].data.actionUrl))
                    .navigation()
        }
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        mRecyclerView.addItemDecoration(GridItemDecoration(2, 4, true))
        mRecyclerView.adapter = adapter
    }


    override fun showNetError(onClickListener: View.OnClickListener) {
        mStateView.showNetError(onClickListener)
    }

    override fun showContent() {
        mStateView.showContent()
    }

    override fun getContentViewLayoutId() = R.layout.activity_all_category
}