package com.jennifer.andy.simpleeyes.ui.feed

import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityAllCategoryBinding
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter.Companion.RECTANGLE_CARD_TYPE
import com.jennifer.andy.simpleeyes.widget.GridItemDecoration
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/7/16 14:17
 * Description: 全部分类
 */

class AllCategoryActivity : BaseStateViewActivity<ActivityAllCategoryBinding>() {

    private val mAllCategoryViewModel: AllCategoryViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {

        initToolBar(mDataBinding.toolBar, R.string.all_category)

        mAllCategoryViewModel.loadAllCategoriesInfo()

        mAllCategoryViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)
    }

    private fun onNewStateArrive(viewState: ViewState<AndyInfo>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                loadAllCategoriesSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mAllCategoryViewModel.loadAllCategoriesInfo() }
            }
        }
    }

    private fun loadAllCategoriesSuccess(andyInfo: AndyInfo) {
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
        mDataBinding.rvRecycler.layoutManager = GridLayoutManager(this, 2)
        mDataBinding.rvRecycler.addItemDecoration(GridItemDecoration(2, 4, true))
        mDataBinding.rvRecycler.adapter = adapter
    }


    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView


    override fun getContentViewLayoutId() = R.layout.activity_all_category
}