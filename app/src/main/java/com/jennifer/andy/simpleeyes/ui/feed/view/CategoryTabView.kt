package com.jennifer.andy.simpleeyes.ui.feed.view

import com.jennifer.andy.simpleeyes.entity.Category
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:47
 * Description:
 */

interface CategoryTabView : BaseView {
    /**
     * 加载分类标签成功
     */
    fun showLoadTabSuccess(category: Category)

}