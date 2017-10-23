package com.jennifer.andy.simpleeyes.ui.category

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.category.model.CategoryModel
import com.jennifer.andy.simpleeyes.ui.category.presenter.CategoryPresenter


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:45
 * Description:首页
 */

class CategoryFragment : BaseFragment<CategoryPresenter, CategoryModel>() {

    companion object {
        fun newInstance(): CategoryFragment = CategoryFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {

    }

}