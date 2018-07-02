package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.category.adapter.DailyEliteAdapter
import com.jennifer.andy.simpleeyes.ui.category.presenter.DailyElitePresenter
import com.jennifer.andy.simpleeyes.ui.category.view.DailyEliteView
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.pull.refresh.LinearLayoutManagerWithSmoothScroller
import com.jennifer.andy.simpleeyes.widget.pull.refresh.PullToRefreshRecyclerView
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:09
 * Description:每日编辑精选
 */

class DailyEliteActivity : BaseActivity<DailyEliteView, DailyElitePresenter>(), DailyEliteView {

    private val mBackImageView: ImageView by bindView(R.id.iv_back)
    private val mRecycler: PullToRefreshRecyclerView by bindView(R.id.rv_recycler)
    private val mTvDate: CustomFontTextView by bindView(R.id.tv_date)

    private var mDailyEliteAdapter: DailyEliteAdapter? = null
    private lateinit var mLinearLayoutManager: LinearLayoutManagerWithSmoothScroller

    override fun initView(savedInstanceState: Bundle?) {

        mPresenter.getDailyElite()

        mRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                //设置当前选择日期
                val position = mLinearLayoutManager.findFirstVisibleItemPosition()
                if (mDailyEliteAdapter?.getItemViewType(position) == DailyEliteAdapter.TEXT_CARD_TYPE) {
                    mTvDate.text = mDailyEliteAdapter?.getItem(position)?.data?.text
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                val layoutManager = mRecycler.rootView.layoutManager as LinearLayoutManager
                //滚动到日期及完毕
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findFirstVisibleItemPosition() == mDailyEliteAdapter!!.getCurrentDayPosition()) {
                    mRecycler.refreshComplete()
                }
            }
        })
        mRecycler.refreshListener = { mPresenter.refresh() }
        mBackImageView.setOnClickListener { finish() }
    }


    override fun showGetDailySuccess(content: MutableList<Content>) {
        if (mDailyEliteAdapter == null) {
            mDailyEliteAdapter = DailyEliteAdapter(content)
            mDailyEliteAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreResult() }, mRecycler.rootView)
            mDailyEliteAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                if (mDailyEliteAdapter?.getItemViewType(position) != DailyEliteAdapter.BANNER_TYPE) {
                    val item = mDailyEliteAdapter?.getItem(position)
                    VideoDetailActivity.start(mContext, item!!.data, mDailyEliteAdapter?.data as ArrayList, position)
                }
            }
            mLinearLayoutManager = LinearLayoutManagerWithSmoothScroller(mContext)
            mRecycler.setAdapterAndLayoutManager(mDailyEliteAdapter!!, mLinearLayoutManager)
        } else {
            mDailyEliteAdapter?.setNewData(content)
        }

        //这里发送延时消息，是因为数据还有可能没有装载完毕
        mRecycler.handler.postDelayed({ mRecycler.smoothScrollToPosition(mDailyEliteAdapter!!.getCurrentDayPosition()) }, 200)

    }

    override fun showRefreshSuccess(content: MutableList<Content>) {
        showGetDailySuccess(content)
    }

    override fun loadMoreSuccess(data: MutableList<Content>) {
        mDailyEliteAdapter?.addData(data)
        mDailyEliteAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mDailyEliteAdapter?.loadMoreEnd()
    }

    override fun initPresenter() = DailyElitePresenter()

    override fun getContentViewLayoutId() = R.layout.activity_daily_elite
}