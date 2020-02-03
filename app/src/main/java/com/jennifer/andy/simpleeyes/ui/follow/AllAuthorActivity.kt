package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityAllAuthorBinding
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2019-07-09 18:22
 * Description: 全部作者
 */

class AllAuthorActivity : BaseStateViewActivity<ActivityAllAuthorBinding>() {

    private var mAdapter: BaseDataAdapter? = null

    private val mFollowViewModel: FollowViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mDataBinding.toolBar, R.string.all_author)
        mFollowViewModel.getAllAuthor()
        mFollowViewModel.observeViewState()
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
                loadAllAuthorSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mFollowViewModel.getFollowInfo() }
            }
            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mFollowViewModel.loadMoreAndyInfo() }
            }
        }
    }

    private fun loadAllAuthorSuccess(it: AndyInfo) {
        mAdapter = BaseDataAdapter(it.itemList)
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        mAdapter?.setOnLoadMoreListener({ mFollowViewModel.loadMoreAndyInfo() }, mDataBinding.rvRecycler)

        mDataBinding.rvRecycler.adapter = mAdapter
        mDataBinding.rvRecycler.layoutManager = LinearLayoutManager(this)

    }


    private fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }


    private fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_all_author


}