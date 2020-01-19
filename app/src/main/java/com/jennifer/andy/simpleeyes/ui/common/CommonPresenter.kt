package com.jennifer.andy.simpleeyes.ui.common

import android.view.View
import com.jennifer.andy.simpleeyes.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2019-08-26 19:51
 * Description:
 */

class CommonPresenter : LoadMorePresenter<AndyInfo, CommonModel, CommonView>() {


    override var mBaseModel = CommonModel()
    /**
     * 获取专题信息
     */
    fun loadDataInfoFromUrl(url: String) {
        mBaseModel.getDataFromUrl(url).autoDispose(mScopeProvider).subscribe({
            mView?.showLoadSuccess(it.itemList)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { loadDataInfoFromUrl(url) })
        })
    }
}