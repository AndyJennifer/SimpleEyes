package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.TopicView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/7/31 15:40
 * Description:
 */

class TopicPresenter : LoadMorePresenter<AndyInfo, FeedModel, TopicView>() {

    override var mBaseModel: FeedModel = FeedModel()

    /**
     * 获取专题信息
     */
    fun getTopicInfo() {
        mBaseModel.getTopicInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showGetTopicInfoSuccess(it.itemList)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { getTopicInfo() })
        })
    }

}