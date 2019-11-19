package com.jennifer.andy.simpleeyes.ui.home.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.home.model.HomeModel
import com.jennifer.andy.simpleeyes.ui.home.view.HomeView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 17:58
 * Description:
 */

class HomePresenter : BasePresenter<HomeView>() {

    private var mHomeModel: HomeModel = HomeModel()
    private var mNextPageUrl: String? = null

    /**
     * 加载首页信息
     */
    fun loadCategoryData() {
        mView?.showLoading()
        mHomeModel.loadCategoryInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadDataSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                loadCategoryData()
            })
        })
    }

    /**
     * 刷新首页信息延迟1秒执行
     */
    fun refreshCategoryData() {
        mHomeModel.refreshCategoryInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.refreshDataSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                refreshCategoryData()
            })
        })
    }

    /**
     * 加载更多首页信息
     */
    fun loadMoreCategoryData() {
        if (mNextPageUrl != null) {
            mHomeModel.loadMoreAndyInfo(mNextPageUrl).autoDispose(mScopeProvider).subscribe({
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
            })
        }
    }
}