package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.TagDetailInfoView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:30
 * Description:
 */

class TagDetailInfoPresenter : LoadMorePresenter<AndyInfo, FeedModel, TagDetailInfoView>() {

    override var mBaseModel: FeedModel = FeedModel()

    /**
     * 获取tab栏下信息
     */
    fun getDetailInfo(url: String) {
        mRxManager.add(mBaseModel.getTabInfo(url).subscribe({
            mView?.showContent()
            mView?.showGetTabInfoSuccess(it)
            mNextPageUrl = it.nextPageUrl
            if (mNextPageUrl == null) mView?.showNoMore()
        }, {
            mView?.showNetError(View.OnClickListener { getDetailInfo(url) })
        }))
    }


}