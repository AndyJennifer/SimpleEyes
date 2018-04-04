package com.jennifer.andy.simpleeyes.ui.search.view

import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 11:06
 * Description:
 */

interface SearchHotView : BaseView {

    /**
     * 获取热门搜索成功
     */
    fun showSearchSuccess(it: List<String>)

}