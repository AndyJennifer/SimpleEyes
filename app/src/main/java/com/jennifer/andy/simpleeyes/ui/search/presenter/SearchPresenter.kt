package com.jennifer.andy.simpleeyes.ui.search.presenter


import android.view.View
import com.jennifer.andy.simpleeyes.base.model.BaseModel
import com.jennifer.andy.simpleeyes.base.presenter.LoadMorePresenter
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.home.domain.HomeRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.search.view.SearchHotView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 11:05
 * Description:
 */

class SearchPresenter : LoadMorePresenter<AndyInfo, BaseModel, SearchHotView>() {

    var mBaseRemoteDataSource: HomeRemoteDataSource = HomeRemoteDataSource()

    /**
     * 获取热门搜索
     */
    fun searchHot() {
        mBaseRemoteDataSource.getHotWord().autoDispose(mScopeProvider).subscribe {
            mView?.getHotWordSuccess(it)
        }
    }

    /**
     * 根据关键字搜索
     */
    fun searchVideoByWord(word: String) {
        mView?.showLoading()
        mBaseRemoteDataSource.searchVideoByWord(word).autoDispose(mScopeProvider).subscribe({
            mView?.showContent()
            mView?.showSearchSuccess(word, it)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { searchVideoByWord(word) })
        })
    }


}