package com.jennifer.andy.simpleeyes.ui.follow.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.view.AllAuthorView


/**
 * Author:  andy.xwt
 * Date:    2019-07-09 18:23
 * Description:
 */

class AllAuthorPresenter : LoadMorePresenter<AndyInfo, FollowModel, AllAuthorView>() {

    override var mBaseModel: FollowModel = FollowModel()

    fun getAllAuthorInfo() {
        mRxManager.add(mBaseModel.getAllAuthor().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadAllAuthorSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getAllAuthorInfo() })
        }))
    }


}