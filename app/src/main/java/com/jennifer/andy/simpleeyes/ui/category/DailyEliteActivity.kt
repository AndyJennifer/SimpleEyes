package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.category.adapter.DailyEliteAdapter
import com.jennifer.andy.simpleeyes.ui.category.presenter.DailyElitePresenter
import com.jennifer.andy.simpleeyes.ui.category.view.DailyEliteView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:09
 * Description:每日编辑精选
 */

class DailyEliteActivity : BaseActivity<DailyEliteView, DailyElitePresenter>(), DailyEliteView {

    private val mBackImageView: ImageView by bindView(R.id.iv_back)
    private val mRecycler: RecyclerView by bindView(R.id.rv_recycler)

    private var mDailyEliteAdapter: DailyEliteAdapter? = null


    override fun initView(savedInstanceState: Bundle?) {
        //todo 加一个粘性的头，推上效果，处理下，滑动冲突，显示头布局
        mPresenter.getDailyElite()
        mBackImageView.setOnClickListener {
            finish()
        }
    }

    override fun showGetDailySuccess(it: MutableList<Content>) {
        if (mDailyEliteAdapter == null) {
            mDailyEliteAdapter = DailyEliteAdapter(it)
            mRecycler.layoutManager = LinearLayoutManager(mContext)
            mRecycler.adapter = mDailyEliteAdapter
        } else {
            mDailyEliteAdapter?.setNewData(it)
        }
    }


    override fun initPresenter() = DailyElitePresenter()

    override fun getContentViewLayoutId() = R.layout.activity_daily_elite
}