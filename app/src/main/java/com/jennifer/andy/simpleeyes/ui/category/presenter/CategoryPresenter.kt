package com.jennifer.andy.simpleeyes.ui.category.presenter

import android.content.Context
import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.category.model.CategoryModel
import com.jennifer.andy.simpleeyes.ui.category.view.CategoryView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 17:58
 * Description:
 */

class CategoryPresenter(mContext: Context) : BasePresenter<CategoryView>(mContext) {

    private var mCategoryModel: CategoryModel = CategoryModel()
    private var mNextPageUrl: String? = null

    /**
     * 加载首页信息
     */
    fun loadCategoryData() {
        mView?.showLoading()
        mRxManager.add(mCategoryModel.loadCategoryInfo().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadDataSuccess(it)
        }, {
            println(it.message)
            mView?.showNetError(View.OnClickListener {
                loadCategoryData()
            })
        }))
    }

    /**
     * 刷新首页信息延迟1秒执行
     */
    fun refreshCategoryData() {
        mRxManager.add(mCategoryModel.refreshCategoryInfo().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.refreshDataSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                refreshCategoryData()
            })
        }))
    }

    /**
     * 加载更多首页信息
     */
    fun loadMoreCategoryData() {
        mRxManager.add(mCategoryModel.loadMoreCategoryInfo(mNextPageUrl).subscribe({
            mView?.showContent()
            if (it.nextPageUrl == null) {
                mView?.showNoMore()
            } else {
                mNextPageUrl = it.nextPageUrl
                mView?.loadMoreSuccess(it)
            }
        }, {
            mView?.showNetError(View.OnClickListener {
                loadMoreCategoryData()
            })
        }))
    }
}