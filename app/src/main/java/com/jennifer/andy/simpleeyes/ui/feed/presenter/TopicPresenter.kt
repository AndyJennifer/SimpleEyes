package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.TopicView


/**
 * Author:  andy.xwt
 * Date:    2018/7/31 15:40
 * Description:
 */

class TopicPresenter : BasePresenter<TopicView>() {

    private var mFeedModel: FeedModel = FeedModel()
    private var mNextPageUrl: String? = null

    /**
     * 获取专题信息
     */
    fun getTopicInfo() {
        mRxManager.add(mFeedModel.getTopicInfo().subscribe({
            mView?.showGetTopicInfoSuccess(it.itemList)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { getTopicInfo() })
        }))
    }

    /**
     * 加载更多首页信息
     */
    fun loadMoreInfo() {
        if (mNextPageUrl != null) {
            mRxManager.add(mFeedModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe({
                mView?.showContent()
                if (it.nextPageUrl == null) {
                    mView?.showNoMore()
                } else {
                    mNextPageUrl = it.nextPageUrl
                    mView?.loadMoreSuccess(it)
                }
            }, {
                mView?.showNetError(View.OnClickListener {
                    loadMoreInfo()
                })
            }))
        }
    }
}