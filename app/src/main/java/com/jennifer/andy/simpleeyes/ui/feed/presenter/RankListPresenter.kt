package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.RankListView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/7/26 18:44
 * Description:
 */

class RankListPresenter : BasePresenter<RankListView>() {

    private var mFeedModel: FeedModel = FeedModel()

    fun getRankListTab() {
        mFeedModel.getRankListTab().autoDispose(mScopeProvider).subscribe({
            mView?.loadTabSuccess(it.tabInfo)
        }, {
            mView?.showNetError(View.OnClickListener { getRankListTab() })
        })
    }

}