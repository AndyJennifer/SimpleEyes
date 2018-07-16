package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedDetailView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:30
 * Description:
 */

class FeedDetailPresenter : BasePresenter<FeedDetailView>() {

    private var mCategoryModel: FeedModel = FeedModel()
    private var mNextPageUrl: String? = null

    /**
     * 获取tab栏下信息
     */
    fun getDetailInfo(url: String) {
        mRxManager.add(mCategoryModel.getTabInfo(url).subscribe({
            mView?.showContent()
            mView?.showGetTabInfoSuccess(it)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { getDetailInfo(url) })
        }))
    }

    /**
     * 加载更多tab栏下信息
     */
    fun loadMoreDetailInfo() {
        mRxManager.add(mCategoryModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe(
                {
                    mView?.showContent()
                    if (it.nextPageUrl == null) {
                        mView?.showNoMore()
                    } else {
                        mNextPageUrl = it.nextPageUrl
                        mView?.loadMoreSuccess(it)
                    }
                },
                {
                    mView?.showNetError(View.OnClickListener {
                        loadMoreDetailInfo()
                    })
                }))
    }
}