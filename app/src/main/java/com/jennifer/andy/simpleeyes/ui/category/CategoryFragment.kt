package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.category.adapter.CategoryAdapter
import com.jennifer.andy.simpleeyes.ui.category.presenter.CategoryPresenter
import com.jennifer.andy.simpleeyes.ui.category.view.CategoryView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:首页
 */

class CategoryFragment : BaseFragment<CategoryView, CategoryPresenter>(), CategoryView {

    private val mRecycler: RecyclerView by bindView(R.id.rv_recycler)
    private var mCateGoryAdapter: CategoryAdapter? = null

    companion object {
        fun newInstance(): CategoryFragment = CategoryFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.loadCategoryData()
    }

    override fun loadDataSuccess(andyInfo: AndyInfo) {
        if (mCateGoryAdapter == null) {
            mCateGoryAdapter = CategoryAdapter(andyInfo.itemList)
            mCateGoryAdapter?.onItemClickListener =
                    BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                        //跳转到详情界面
                        val item = adapter.getItem(position) as AndyInfo.ItemListBeanX
                        startBannerDetailActivity()
                    }
            mCateGoryAdapter?.bindToRecyclerView(mRecycler)
            mRecycler.adapter = mCateGoryAdapter
            mRecycler.layoutManager = LinearLayoutManager(_mActivity)
            mRecycler.itemAnimator = DefaultItemAnimator()
        } else {
            mCateGoryAdapter?.setNewData(andyInfo.itemList)
        }
    }

    override fun getContentViewLayoutId() = R.layout.fragment_category

    override fun initPresenter() = CategoryPresenter(context)

    /**
     * 跳转到视频banner详情界面
     */
    private fun startBannerDetailActivity() {

    }

    /**
     * 跳转到视频followCard详情界面
     */
    private fun startFollowCardDetailActivity() {

    }

}