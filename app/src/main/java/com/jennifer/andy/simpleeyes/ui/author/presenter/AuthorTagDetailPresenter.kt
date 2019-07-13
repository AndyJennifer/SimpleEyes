package com.jennifer.andy.simpleeyes.ui.author.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.author.model.AuthorModel
import com.jennifer.andy.simpleeyes.ui.author.ui.AuthorTagDetailView
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:40
 * Description:
 */

class AuthorTagDetailPresenter : BasePresenter<AuthorTagDetailView>() {

    private var mAuthorModel: AuthorModel = AuthorModel()

    fun getAuthorTagDetail(id: String) {
        mRxManager.add(mAuthorModel.getAuthorTagDetail(id).subscribe({
            mView?.showContent()
            mView?.loadInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener { getAuthorTagDetail(id) })
        }))
    }

}