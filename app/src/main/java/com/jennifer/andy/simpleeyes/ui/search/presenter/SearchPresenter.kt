package com.jennifer.andy.simpleeyes.ui.search.presenter


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

    /**
     * 获取热门搜索
     */
    fun searchHot() {
        mRxManager.add(mCategoryModel.getHotWord().subscribe({
            mView?.getHotWordSuccess(it)
        }))
    }

    fun searchVideoByWord(word: String) {
        mRxManager.add(mCategoryModel.searchVideoByWrod(word).subscribe({
            mView?.showSearchSuccess(it)
        }, {
            mView?.showSearchFail(word)
        }))
    }

}