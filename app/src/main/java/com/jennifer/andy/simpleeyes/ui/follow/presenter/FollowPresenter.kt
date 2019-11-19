package com.jennifer.andy.simpleeyes.ui.follow.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.view.FollowView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

class FollowPresenter : LoadMorePresenter<AndyInfo, FollowModel, FollowView>() {


    override var mBaseModel: FollowModel = FollowModel()

    fun getFollowInfo() {
        mBaseModel.getFollowInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadFollowInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getFollowInfo() })
        })
    }


    fun refresh() {
        mBaseModel.getFollowInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.refreshSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                refresh()
            })
        })
    }
}