package com.jennifer.andy.simpleeyes.ui.follow.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.view.FollowView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

class FollowPresenter : LoadMorePresenter<AndyInfo, FollowModel, FollowView>() {


    override var mBaseModel: FollowModel = FollowModel()

    fun getFollowInfo() {
        mRxManager.add(mBaseModel.getFollowInfo().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadFollowInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getFollowInfo() })
        }))
    }


    fun refresh() {
        mRxManager.add(mBaseModel.getFollowInfo().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.refreshSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                refresh()
            })
        }))
    }
}