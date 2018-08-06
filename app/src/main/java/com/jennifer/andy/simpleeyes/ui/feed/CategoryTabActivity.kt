package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.feed.presenter.CategroyTabPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.CategoryTabView


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:46
 * Description:
 */

class CategoryTabActivity : BaseActivity<CategoryTabView, CategroyTabPresenter>() {

    override fun initView(savedInstanceState: Bundle?) {
        //todo NestedScrollingChild 与NestedScrollingParent机制。来做
    }


    override fun getContentViewLayoutId() = R.layout.activity_category_tab

}