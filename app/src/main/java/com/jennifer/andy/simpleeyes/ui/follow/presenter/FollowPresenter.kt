package com.jennifer.andy.simpleeyes.ui.follow.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.follow.model.FollowModel
import com.jennifer.andy.simpleeyes.ui.follow.view.FollowView


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

class FollowPresenter : BasePresenter<FollowView>() {


    private var mFollowModel: FollowModel = FollowModel()
    private var mNextPageUrl: String? = null

    fun getFollowInfo() {
        mRxManager.add(mFollowModel.getFollowInfo().subscribe({
            mView?.showContent()
            mView?.loadFollowInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getFollowInfo() })
        }))
    }


    /**
     * 加载更多首页信息
     */
    fun loadMoreFollowInfo() {
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
                    loadMoreFollowInfo()
                })
            }))
        }
    }

    fun refresh() {
        mRxManager.add(mFollowModel.getFollowInfo().subscribe({
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