package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennifer.andy.base.utils.readyGo
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentFollowBinding
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewFragment
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description: 关注
 */

class FollowFragment : BaseStateViewFragment<FragmentFollowBinding>() {

    private var mAdapter: BaseDataAdapter? = null
    private val mFollowViewModel: FollowViewModel by currentScope.viewModel(this)

    companion object {
        fun newInstance() = FollowFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        //获取主界面信息
        mFollowViewModel.getFollowInfo()

        //跳转到搜索界面
        mDataBinding.ivSearch.setOnClickListener {
            readyGo<SearchHotActivity>()
        }
        //跳转到全部作者界面
        mDataBinding.tvAllAuthor.setOnClickListener {
            readyGo<AllAuthorActivity>()
        }

        mDataBinding.rvFollowRecycler.refreshListener = { mFollowViewModel.refreshFollowInfo() }

        mFollowViewModel
                .observeViewState()
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
                loadFollowInfoSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mFollowViewModel.getFollowInfo() }
            }
            Action.REFRESH_SUCCESS -> {
                showContent()
                refreshSuccess(viewState.data!!)
            }
            Action.REFRESH_FAIL -> {
                showNetError { mFollowViewModel.refreshFollowInfo() }
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

    private fun loadFollowInfoSuccess(andyInfo: AndyInfo) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(andyInfo.itemList)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mAdapter?.setOnLoadMoreListener({ mFollowViewModel.loadMoreAndyInfo() }, mDataBinding.rvFollowRecycler.rootView)

            mDataBinding.rvFollowRecycler.setAdapterAndLayoutManager(mAdapter!!, LinearLayoutManager(context))
        } else {
            mAdapter?.setNewData(andyInfo.itemList)
        }
    }


    fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }


    fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }

    private fun refreshSuccess(andyInfo: AndyInfo) {
        loadFollowInfoSuccess(andyInfo)
        mDataBinding.rvFollowRecycler.refreshComplete()
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.fragment_follow

}
