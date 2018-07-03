package com.jennifer.andy.simpleeyes.ui.search.presenter


import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.category.model.CategoryModel
import com.jennifer.andy.simpleeyes.ui.search.view.SearchHotView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 11:05
 * Description:
 */

class SearchPresenter : BasePresenter<SearchHotView>() {

    private var mCategoryModel: CategoryModel = CategoryModel()
    private var mNextPageUrl: String? = null

    /**
     * 获取热门搜索
     */
    fun searchHot() {
        mRxManager.add(mCategoryModel.getHotWord().subscribe {
            mView?.getHotWordSuccess(it)
        })
    }

    /**
     * 根据关键字搜索
     */
    fun searchVideoByWord(word: String) {
        mView?.showLoading()
        mRxManager.add(mCategoryModel.searchVideoByWord(word).subscribe({
            mView?.showContent()
            mView?.showSearchSuccess(word, it)
            mNextPageUrl = it.nextPageUrl
        }, {
            mView?.showNetError(View.OnClickListener { searchVideoByWord(word) })
        }))
    }

    /**
     * 获取更多搜索结果
     */
    fun loadMoreSearchResult() {
        if (mNextPageUrl != null) {
            mRxManager.add(mCategoryModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe({
                mView?.showContent()
                if (it.nextPageUrl == null) {
                    mView?.showNoMore()
                } else {
                    mNextPageUrl = it.nextPageUrl
                    mView?.loadMoreSuccess(it)
                }
            }, {
                mView?.showNetError(View.OnClickListener {
                    loadMoreSearchResult()
                })
            }))
        }
    }

}