package com.jennifer.andy.simpleeyes.ui.search.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 11:06
 * Description:
 */

interface SearchHotView : BaseView {

    /**
     * 获取热门关键词成功
     */
    fun getHotWordSuccess(hotList: MutableList<String>)

    /**
     * 搜索成功
     */
    fun showSearchSuccess(andyInfo: AndyInfo)

    /**
     * 搜索失败
     */
    fun showSearchFail(word: String)

}