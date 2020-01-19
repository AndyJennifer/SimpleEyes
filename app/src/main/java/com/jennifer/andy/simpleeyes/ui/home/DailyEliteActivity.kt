package com.jennifer.andy.simpleeyes.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityDailyEliteBinding
import com.jennifer.andy.simpleeyes.net.entity.Content
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.home.adapter.DailyEliteAdapter
import com.jennifer.andy.simpleeyes.widget.pull.refresh.LinearLayoutManagerWithSmoothScroller
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:09
 * Description:每日编辑精选
 */

class DailyEliteActivity : BaseStateViewActivity<ActivityDailyEliteBinding>() {

    private var mDailyEliteAdapter: DailyEliteAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManagerWithSmoothScroller

    private val mDailyEliteModel: DailyEliteViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {

        mDataBinding.rvRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //设置当前选择日期
                val position = mLinearLayoutManager.findFirstVisibleItemPosition()
                if (mDailyEliteAdapter?.getItemViewType(position) == BaseDataAdapter.TEXT_CARD_TYPE) {
                    mDataBinding.tvDate.text = mDailyEliteAdapter?.getItem(position)?.data?.text
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val layoutManager = mDataBinding.rvRecycler.rootView.layoutManager as LinearLayoutManager
                //滚动到日期及完毕
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findFirstVisibleItemPosition() == mDailyEliteAdapter!!.getCurrentDayPosition()) {
                    mDataBinding.rvRecycler.refreshComplete()
                }
            }
        })
        mDataBinding.rvRecycler.refreshListener = { mDailyEliteModel.refresh() }
        mDataBinding.ivBack.setOnClickListener { finish() }

        mDailyEliteModel.getDailyElite()

        mDailyEliteModel.observeViewState()
                .autoDispose(this)
                .subscribe(this::onNewStateArrive)

    }


    private fun onNewStateArrive(viewState: ViewState<JenniferInfo>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                showGetDailySuccess(combineContentInfo(viewState.data!!))
            }
            Action.INIT_FAIL -> {
                showNetError { mDailyEliteModel.getDailyElite() }
            }
            Action.REFRESH_SUCCESS -> {
                showContent()
                showRefreshSuccess(combineContentInfo(viewState.data!!))
            }
            Action.REFRESH_FAIL -> {
                showNetError { mDailyEliteModel.refresh() }
            }
            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(combineContentInfo(viewState.data!!))
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mDailyEliteModel.loadMoreJenniferInfo() }
            }
        }
    }

    private fun combineContentInfo(jenniferInfo: JenniferInfo): MutableList<Content> {
        val list = mutableListOf<Content>()
        val issueList = jenniferInfo.issueList
        for (contentBean in issueList) {
            val itemList = contentBean.itemList
            for (content in itemList) {
                list.add(content)
            }
        }
        return list
    }

    private fun showGetDailySuccess(content: MutableList<Content>) {
        if (mDailyEliteAdapter == null) {
            mDailyEliteAdapter = DailyEliteAdapter(content)
            mDailyEliteAdapter?.setOnLoadMoreListener({ mDailyEliteModel.loadMoreJenniferInfo() }, mDataBinding.rvRecycler.rootView)
            mLinearLayoutManager = LinearLayoutManagerWithSmoothScroller(this)
            mDataBinding.rvRecycler.setAdapterAndLayoutManager(mDailyEliteAdapter!!, mLinearLayoutManager)
        } else {
            mDailyEliteAdapter?.setNewData(content)
        }
        //这里有可能加载的每日精选，没有text类型，就会导致刷新完毕后，刷新界面没有消失
        if (mDailyEliteAdapter!!.getCurrentDayPosition() != 0) {
            //这里发送延时消息，是因为数据还有可能没有装载完毕
            mDataBinding.rvRecycler.handler.postDelayed({ mDataBinding.rvRecycler.smoothScrollToPosition(mDailyEliteAdapter!!.getCurrentDayPosition()) }, 200)
        } else {
            mDataBinding.rvRecycler.refreshComplete()
        }

    }

    private fun showRefreshSuccess(content: MutableList<Content>) {
        showGetDailySuccess(content)
    }

    private fun loadMoreSuccess(data: MutableList<Content>) {
        mDailyEliteAdapter?.addData(data)
        mDailyEliteAdapter?.loadMoreComplete()
    }

    private fun showNoMore() {
        mDailyEliteAdapter?.loadMoreEnd()
    }


    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_daily_elite
}