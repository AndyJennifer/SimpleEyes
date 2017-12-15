package com.jennifer.andy.simpleeyes.ui.category.view

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseView


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 17:59
 * Description:
 */

interface CategoryView : BaseView {

    /**
     * 加载信息成功
     */
    fun loadDataSuccess(andyInfo: AndyInfo)

    /**
     * 加载信息成功
     */
    fun refreshDataSuccess(andyInfo: AndyInfo)

}