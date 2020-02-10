package com.jennifer.andy.simpleeyes.ui.common

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityCommonRecyclerviewBinding
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.Content
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
 * Date:    2019-08-26 19:47
 * Description:公共RecyclerView界面，根据url加载数据
 */

@Route(path = "/AndyJennifer/common")
class CommonRecyclerActivity : BaseStateViewActivity<ActivityCommonRecyclerviewBinding>() {

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var url: String? = null

    private var mCommonAdapter: BaseDataAdapter? = null
    private val mCommonViewModel: CommonRecyclerViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mDataBinding.toolBar, title)
        bind()
        mCommonViewModel.getDataFromUrl(url!!)
    }


    private fun bind() {
        mCommonViewModel.observeDataInfo()
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
                showLoadSuccess(viewState.data!!.itemList)
            }
            Action.INIT_FAIL -> {
                showNetError { mCommonViewModel.getDataFromUrl(url!!) }
            }

            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mCommonViewModel.loadMoreDataFromUrl() }
            }
        }
    }

    private fun showLoadSuccess(itemList: MutableList<Content>) {
        with(mDataBinding.rvRecycler) {
            mCommonAdapter = BaseDataAdapter(itemList).apply {
                setOnLoadMoreListener({ mCommonViewModel.loadMoreDataFromUrl() }, this@with)
                setLoadMoreView(CustomLoadMoreView())
            }
            layoutManager = LinearLayoutManager(this@CommonRecyclerActivity)
            adapter = mCommonAdapter
        }
    }

    private fun loadMoreSuccess(data: AndyInfo) {
        mCommonAdapter?.addData(data.itemList)
        mCommonAdapter?.loadMoreComplete()
    }

    private fun showNoMore() {
        mCommonAdapter?.loadMoreEnd()
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_common_recyclerview

}