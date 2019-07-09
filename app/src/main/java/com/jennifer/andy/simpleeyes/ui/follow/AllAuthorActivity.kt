package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.follow.presenter.AllAuthorPresenter
import com.jennifer.andy.simpleeyes.ui.follow.view.AllAuthorView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2019-07-09 18:22
 * Description: 全部作者
 */

class AllAuthorActivity : BaseActivity<AllAuthorView, AllAuthorPresenter>(), AllAuthorView {

    private val mToolBar: RelativeLayout by bindView(R.id.tool_bar)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)
    private var mAdapter: BaseDataAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        initToolBar(mToolBar, R.string.all_author)
        mPresenter.getAllAuthorInfo()
    }


    override fun loadAllAuthorSuccess(it: AndyInfo) {
        mAdapter = BaseDataAdapter(it.itemList)
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        mAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreInfo() }, mRecyclerView)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun showNetError(onClickListener: View.OnClickListener) {
        mStateView.showNetError(onClickListener)
    }

    override fun showContent() {
        mStateView.showContent()
    }

    override fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }


    override fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }


    override fun getContentViewLayoutId() = R.layout.activity_all_author


}