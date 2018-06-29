package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.category.adapter.DailyEliteAdapter
import com.jennifer.andy.simpleeyes.ui.category.presenter.DailyElitePresenter
import com.jennifer.andy.simpleeyes.ui.category.view.DailyEliteView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.pull.refresh.LinearLayoutManagerWithSmoothScroller
import com.jennifer.andy.simpleeyes.widget.pull.refresh.PullToRefreshRecyclerView


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

        })

        mRecycler.refreshListener = { mPresenter.getDailyElite() }
        mBackImageView.setOnClickListener { finish() }

    }


    override fun showGetDailySuccess(it: MutableList<Content>) {
        if (mDailyEliteAdapter == null) {
            mDailyEliteAdapter = DailyEliteAdapter(it)
            mLinearLayoutManager = LinearLayoutManagerWithSmoothScroller(mContext)
            mRecycler.setAdapterAndLayoutManager(mDailyEliteAdapter!!, mLinearLayoutManager)
            //这里发送延时消息，是因为数据还有可能没有装载完毕
            mRecycler.handler.postDelayed({ mRecycler.smoothScrollToPosition(mDailyEliteAdapter!!.getCurrentDayPosition()) }, 200)
        } else {
            mDailyEliteAdapter?.setNewData(it)
        }
    }

    override fun loadMoreSuccess(data: MutableList<Content>) {

    }

    override fun showNoMore() {

    }

    override fun initPresenter() = DailyElitePresenter()

    override fun getContentViewLayoutId() = R.layout.activity_daily_elite
}