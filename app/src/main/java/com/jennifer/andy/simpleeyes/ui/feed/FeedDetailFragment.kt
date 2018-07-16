package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.category.adapter.CategoryAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedDetailPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedDetailView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:29
 * Description:发现中详细的Tab界面
 */

class FeedDetailFragment : BaseFragment<FeedDetailView, FeedDetailPresenter>(), FeedDetailView {


    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)
    private var mCateGoryAdapter: CategoryAdapter? = null

    private lateinit var mApiUrl: String

    companion object {
        fun newInstance(apiUrl: String): FeedDetailFragment {
            val categoryFragment = FeedDetailFragment()
            val bundle = Bundle()
            bundle.putString(Extras.API_URL, apiUrl)
            categoryFragment.arguments = bundle
            return categoryFragment
        }
    }

    override fun getBundleExtras(extras: Bundle) {
        mApiUrl = extras.getString(Extras.API_URL)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.getDetailInfo(mApiUrl)
    }


    override fun showGetTabInfoSuccess(andyInfo: AndyInfo) {
        if (mCateGoryAdapter == null) {
            mCateGoryAdapter = CategoryAdapter(andyInfo.itemList)
            mCateGoryAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreDetailInfo() }, mRecyclerView)

            mRecyclerView.adapter = mCateGoryAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            mCateGoryAdapter?.setNewData(andyInfo.itemList)
        }
    }

    override fun loadMoreSuccess(data: AndyInfo) {
        mCateGoryAdapter?.addData(data.itemList)
        mCateGoryAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mCateGoryAdapter?.loadMoreEnd()
    }

    override fun initPresenter() = FeedDetailPresenter()


    override fun getContentViewLayoutId() = R.layout.fragment_feed_detail
}