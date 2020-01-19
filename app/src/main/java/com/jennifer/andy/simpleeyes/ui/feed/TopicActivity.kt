package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityTopicBinding
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
 * Date:    2018/7/31 15:37
 * Description:专题
 */
@Route(path = "/campaign/list")
class TopicActivity : BaseStateViewActivity<ActivityTopicBinding>() {

    private var mAdapter: BaseDataAdapter? = null

    private val mTopicViewModel: TopicViewModel by currentScope.viewModel(this)

    @Autowired
    @JvmField
    var title: String? = null

    override fun initView(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        initToolBar(mDataBinding.toolBar, title)

        mTopicViewModel.getTopicInfo()

        mTopicViewModel.observeViewState()
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
                showGetTopicInfoSuccess(viewState.data!!.itemList)
            }
            Action.INIT_FAIL -> {
                showNetError { mTopicViewModel.getTopicInfo() }
            }
            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mTopicViewModel.loadMoreInfo() }
            }
        }
    }

    private fun showGetTopicInfoSuccess(itemList: MutableList<Content>) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(itemList)
            mDataBinding.rvRecycler
            mAdapter?.setOnLoadMoreListener({ mTopicViewModel.loadMoreInfo() }, mDataBinding.rvRecycler)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mDataBinding.rvRecycler.adapter = mAdapter
            mDataBinding.rvRecycler.layoutManager = LinearLayoutManager(this)
        } else {
            mAdapter?.setNewData(itemList)
        }
    }

    private fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }

    private fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_topic
}