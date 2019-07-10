package com.jennifer.andy.simpleeyes.ui.follow

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.follow.presenter.FollowPresenter
import com.jennifer.andy.simpleeyes.ui.follow.view.FollowView
import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.pull.refresh.PullToRefreshRecyclerView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description: 关注
 */

class FollowFragment : BaseFragment<FollowView, FollowPresenter>(), FollowView {


    private val mTvAllAuthor: CustomFontTextView by bindView(R.id.tv_all_author)
    private val mIvSearch: ImageView by bindView(R.id.iv_search)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)
    private val mRecyclerView: PullToRefreshRecyclerView by bindView(R.id.rv_follow_recycler)
    private var mAdapter: BaseDataAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(): FollowFragment = FollowFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        //获取主界面信息

        mPresenter.getFollowInfo()

        //跳转到搜索界面
        mIvSearch.setOnClickListener {
            readyGo(SearchHotActivity::class.java, null)
        }
        //跳转到全部作者界面
        mTvAllAuthor.setOnClickListener {
            readyGo(AllAuthorActivity::class.java)
        }

        mRecyclerView.refreshListener = { mPresenter.refresh() }
    }

    override fun loadFollowInfoSuccess(andyInfo: AndyInfo) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(andyInfo.itemList)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreInfo() }, mRecyclerView.rootView)

            mRecyclerView.setAdapterAndLayoutManager(mAdapter!!, LinearLayoutManager(context))
        } else {
            mAdapter?.setNewData(andyInfo.itemList)
        }
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

    override fun refreshSuccess(andyInfo: AndyInfo) {
        loadFollowInfoSuccess(andyInfo)
        mRecyclerView.refreshComplete()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_follow

}
