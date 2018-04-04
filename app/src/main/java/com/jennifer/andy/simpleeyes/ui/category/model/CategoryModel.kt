package com.jennifer.andy.simpleeyes.ui.category.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:00
 * Description:首页model
 */

class CategoryModel : BaseModel {

    /**
     * 加载首页信息
     */
    fun loadCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getCategory().compose(RxHelper.handleResult())

    /**
     * 刷新主页信息，延迟1秒执行
     */
    fun refreshCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getCategory().delay(1000, TimeUnit.MILLISECONDS).compose(RxHelper.handleResult())

    /**
     * 加载更多主页信息
     */
    fun loadMoreCategoryInfo(nextPageUrl: String?) = Api.getDefault().getMoreCategoryInfo(nextPageUrl).compose(RxHelper.handleResult())


    /**
     * 首页关键词搜索
     */
    fun searchHot(): Observable<List<String>> = Api.getDefault().getCategoryHot().compose(RxHelper.handleResult())
}