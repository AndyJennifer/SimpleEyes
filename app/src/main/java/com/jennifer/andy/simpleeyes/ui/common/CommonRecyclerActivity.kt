package com.jennifer.andy.simpleeyes.ui.common

import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.base.adapter.BaseDataAdapter
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView


/**
 * Author:  andy.xwt
 * Date:    2019-08-26 19:47
 * Description:公共RecyclerView界面，根据url加载数据
 */

@Route(path = "/AndyJennifer/common")
class CommonRecyclerActivity : BaseActivity<CommonView, CommonPresenter>(), CommonView {

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var url: String? = null

    private val mToolbar: RelativeLayout by bindView(R.id.tool_bar)
    private val mRecycler by bindView<RecyclerView>(R.id.rv_recycler)
    private var mCommonAdapter: BaseDataAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mToolbar, title)
        mPresenter.loadDataInfoFromUrl(url)
    }

    override fun showLoadSuccess(itemList: MutableList<Content>) {
        with(mRecycler) {
            mCommonAdapter = BaseDataAdapter(itemList).apply {
                setOnLoadMoreListener({ mPresenter.loadMoreInfo() }, mRecycler)
                setLoadMoreView(CustomLoadMoreView())
            }
            layoutManager = LinearLayoutManager(mContext)
            adapter = mCommonAdapter
        }
    }

    override fun loadMoreSuccess(data: AndyInfo) {
        mCommonAdapter?.addData(data.itemList)
        mCommonAdapter?.loadMoreComplete()
    }

    override fun showNoMore() {
        mCommonAdapter?.loadMoreEnd()
    }

    override fun getContentViewLayoutId() = R.layout.activity_common_recyclerview

}