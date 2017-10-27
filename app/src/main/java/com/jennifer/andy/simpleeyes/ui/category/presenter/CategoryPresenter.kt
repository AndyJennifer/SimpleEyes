package com.jennifer.andy.simpleeyes.ui.category.presenter

import android.content.Context
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

    fun loadCategoryData() {
        mRxManager.add(categoryModel.loadCategoryInfo().subscribe({
            println(it.count)
            mView?.loadDataSuccess(it)
        }, {
            println("fuck")
        }, {
            println("success")
        }))
    }
}