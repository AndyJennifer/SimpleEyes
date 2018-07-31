package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.ui.feed.presenter.TopicPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.TopicView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2018/7/31 15:37
 * Description:专题
 */
@Route(path = "/campaign/list")
class TopicActivity : BaseActivity<TopicView, TopicPresenter>(), TopicView {

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mRecyclerView: RecyclerView by bindView(R.id.rv_recycler)
    private val mStateView: MultipleStateView by bindView(R.id.multiple_state_view)
    private var mAdapter: BaseDataAdapter? = null

    @Autowired
    @JvmField
    var title: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mToolbar, title)
        mPresenter.getTopicInfo()
    }

    override fun showGetTopicInfoSuccess(itemList: MutableList<Content>) {
        if (mAdapter == null) {
            mAdapter = BaseDataAdapter(itemList)
            mAdapter?.setOnLoadMoreListener({ mPresenter.loadMoreInfo() }, mRecyclerView)
            mAdapter?.setLoadMoreView(CustomLoadMoreView())
            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        } else {
            mAdapter?.setNewData(itemList)
        }
    }

    override fun loadMoreSuccess(data: AndyInfo) {
        mAdapter?.addData(data.itemList)
        mAdapter?.loadMoreComplete()
    }


    override fun showNoMore() {
        mAdapter?.loadMoreEnd()
    }

    override fun showNetError(onClickListener: View.OnClickListener) {
        mStateView.showNetError(onClickListener)
    }

    override fun getContentViewLayoutId() = R.layout.activity_topic
}