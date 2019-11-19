package com.jennifer.andy.simpleeyes.ui.feed.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.feed.model.FeedModel
import com.jennifer.andy.simpleeyes.ui.feed.view.AllCategoryView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/7/16 14:18
 * Description:
 */

class AllCategoryPresenter : BasePresenter<AllCategoryView>() {

    private var mFeedModel: FeedModel = FeedModel()

    fun loadAllCategoriesInfo() {
        mFeedModel.loadAllCategoriesInfo().autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mView?.loadAllCategoriesSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { loadAllCategoriesInfo() })
        })

    }
}