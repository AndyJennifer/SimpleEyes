package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.TagDetailInfoPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.TagDetailInfoView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:29
 * Description:详细的Tab界面
 */

class TagDetailInfoFragment : BaseFragment<TagDetailInfoView, TagDetailInfoPresenter>(), TagDetailInfoView {


    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)
    private var mAdapter: BaseDataAdapter? = null

    private lateinit var mApiUrl: String

    companion object {

        @JvmStatic
        fun newInstance(apiUrl: String): TagDetailInfoFragment {
            val categoryFragment = TagDetailInfoFragment()
            val bundle = Bundle()
            bundle.putString(Extras.API_URL, apiUrl)
            categoryFragment.arguments = bundle
            return categoryFragment
        }
    }

    override fun getBundleExtras(extras: Bundle) {
        mApiUrl = extras.getString(Extras.API_URL)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        mPresenter.getDetailInfo(mApiUrl)
    }

    override fun showGetTabInfoSuccess(andyInfo: AndyInfo) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(andyInfo.itemList)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreDetailInfo() }, mRecyclerView)
            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            mAdapter?.setNewData(andyInfo.itemList)
        }
    }

    override fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_tag_detail_info
}