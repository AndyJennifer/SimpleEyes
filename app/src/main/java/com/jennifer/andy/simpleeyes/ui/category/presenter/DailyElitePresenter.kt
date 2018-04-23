package com.jennifer.andy.simpleeyes.ui.category.presenter

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

    fun getDailyElite() {
        mRxManager.add(mAndyModel.getDailyElite().subscribe({
            mView?.showGetDailySuccess(combineContentInfo(it))
        }, {

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