package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:52
 * Description:
 */

class FeedPresenter : BasePresenter<FeedView>() {

    private var mFeedModel: FeedModel = FeedModel()

    /**
     * 获取导航栏信息
     */
    fun getDiscoveryTab() {
        mFeedModel.getDiscoveryTab().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mView?.loadTabSuccess(it.tabInfo)
        }, {
            mView?.showNetError(View.OnClickListener { getDiscoveryTab() })
        })
    }

}