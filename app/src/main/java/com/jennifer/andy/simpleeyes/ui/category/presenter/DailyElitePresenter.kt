package com.jennifer.andy.simpleeyes.ui.category.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.category.model.AndyModel
import com.jennifer.andy.simpleeyes.ui.category.view.DailyEliteView


/**
 * Author:  andy.xwt
 * Date:    2018/4/20 16:12
 * Description:
 */

class DailyElitePresenter : BasePresenter<DailyEliteView>() {

    private var mAndyModel: AndyModel = AndyModel()
    private var mNextPageUrl: String? = null

    fun getDailyElite() {
        mRxManager.add(mAndyModel.getDailyElite().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showGetDailySuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        }))
    }

    /**
     * 刷新
     */
    fun refresh() {
        mRxManager.add(mAndyModel.getDailyElite().subscribe({
            mView?.showContent()
            mNextPageUrl = it.nextPageUrl
            mView?.showRefreshSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                refresh()
            })
        }))
    }

    /**
     * 加載更多
     */
    fun loadMoreResult() {
        mRxManager.add(mAndyModel.loadMoreJenniferInfo(mNextPageUrl)!!.subscribe({
            mView?.loadMoreSuccess(combineContentInfo(it))
        }, {
            mView?.showNetError(View.OnClickListener {
                getDailyElite()
            })
        }))
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