package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.feed.presenter.CategroyTabPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.CategoryTabView


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:46
 * Description:种类界面，包括广告、剧情等同类型的分类。
 */

@Route(path = "/AndyJennifer/category")
class CategoryTabActivity : BaseActivity<CategoryTabView, CategroyTabPresenter>() {


    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var id: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        //todo NestedScrollingChild 与NestedScrollingParent机制。来做
    }


    override fun getContentViewLayoutId() = R.layout.activity_category_tab

}