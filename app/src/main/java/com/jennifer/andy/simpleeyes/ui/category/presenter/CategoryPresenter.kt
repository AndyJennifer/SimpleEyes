package com.jennifer.andy.simpleeyes.ui.category.presenter

import android.content.Context
import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.category.model.CategoryModel
import com.jennifer.andy.simpleeyes.ui.category.view.CategoryView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 17:58
 * Description:
 */

class CategoryPresenter(mContext: Context) : BasePresenter<CategoryView>(mContext) {

    private var categoryModel: CategoryModel = CategoryModel()

    /**
     * 加载首页信息
     */
    fun loadCategoryData() {
        mView?.showLoading()
        mRxManager.add(categoryModel.loadCategoryInfo().subscribe({
            mView?.showContent()
            mView?.loadDataSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                loadCategoryData()
            })
        }))
    }
}