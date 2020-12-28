package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentTagDetailInfoBinding
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewFragment
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
 * Date:    2018/7/3 11:29
 * Description:详细的Tab界面,该fragment在ViewPager中是懒加载的，关于如何懒加载查看[com.jennifer.andy.base.ui.LazyFragment]
 */

class TagDetailInfoFragment : BaseStateViewFragment<FragmentTagDetailInfoBinding>() {

    private var mAdapter: BaseDataAdapter? = null

    private lateinit var mApiUrl: String

    private val mTagDetailViewModel: TagDetailViewModel by currentScope.viewModel(this)

    companion object {
        fun newInstance(apiUrl: String) = TagDetailInfoFragment().apply {
            arguments = Bundle().apply { putString(Extras.API_URL, apiUrl) }
        }
    }

    override fun initViewCreated(savedInstanceState: Bundle?) {}

    override fun lazyInit() {
        mTagDetailViewModel.getDataFromUrl(mApiUrl)

        mTagDetailViewModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)
    }

    override fun getBundleExtras(extras: Bundle?) {
        mApiUrl = extras?.getString(Extras.API_URL).toString()
    }

    private fun onNewStateArrive(viewState: ViewState<AndyInfo>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                showGetTabInfoSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mTagDetailViewModel.getDataFromUrl(mApiUrl) }
            }
            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mTagDetailViewModel.loadMoreDataFromUrl() }
            }
        }
    }


    private fun showGetTabInfoSuccess(andyInfo: AndyInfo) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(andyInfo.itemList)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mAdapter?.setOnLoadMoreListener({ mTagDetailViewModel.loadMoreDataFromUrl() }, mDataBinding.rvRecycler)
            mDataBinding.rvRecycler.adapter = mAdapter
            mDataBinding.rvRecycler.layoutManager = LinearLayoutManager(context)
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

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.fragment_tag_detail_info
}