package com.jennifer.andy.simpleeyes.ui.home.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.home.model.HomeModel
import com.jennifer.andy.simpleeyes.ui.home.view.DailyEliteView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:12
 * Description:
 */

class DailyElitePresenter : BasePresenter<DailyEliteView>() {

    private var mHomeModel: HomeModel = HomeModel()
    private var mNextPageUrl: String? = null

    fun getDailyElite() {
        mHomeModel.getDailyElite().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showGetDailySuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        })
    }

    /**
     * 刷新
     */
    fun refresh() {
        mHomeModel.getDailyElite().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showRefreshSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                refresh()
            })
        })
    }

    /**
     * 加載更多
     */
    fun loadMoreResult() {
        mHomeModel.loadMoreJenniferInfo(mNextPageUrl).autoDispose(mScopeProvider).subscribe({
            mNextPageUrl = it.nextPageUrl
            mView?.loadMoreSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        })
    }

    private fun combineContentInfo(jenniferInfo: JenniferInfo): MutableList<Content> {
        val list = mutableListOf<Content>()
        val issueList = jenniferInfo.issueList
        for (contentBean in issueList) {
            val itemList = contentBean.itemList
            for (content in itemList) {
                list.add(content)
            }
        }
        return list
    }
}