package com.jennifer.andy.simpleeyes.ui.follow.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.view.AllAuthorView


/**
 * Author:  andy.xwt
 * Date:    2019-07-09 18:23
 * Description:
 */

class AllAuthorPresenter : BasePresenter<AllAuthorView>() {

    private var mFollowModel: FollowModel = FollowModel()
    private var mNextPageUrl: String? = null


    fun getAllAuthorInfo() {
        mRxManager.add(mFollowModel.getAllAuthor().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.loadAllAuthorSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getAllAuthorInfo() })
        }))
    }

    fun loadMoreInfo() {
        if (mNextPageUrl != null) {
            mRxManager.add(mFollowModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe({
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