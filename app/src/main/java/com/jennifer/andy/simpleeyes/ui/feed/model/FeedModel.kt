package com.jennifer.andy.simpleeyes.ui.feed.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:52
 * Description:
 */

class FeedModel : BaseModel {

    /**
     * 获取发现tab栏
     */
    fun getDiscoveryTab(): Observable<Tab> = Api.getDefault().getDiscoveryTab().compose(RxHelper.handleResult())

    /**
     * 获取tab信息
     */
    fun getTabInfo(url: String): Observable<AndyInfo> = Api.getDefault().getTabInfo(url).compose(RxHelper.handleResult())

    /**
     * 加载更多信息
     */
    fun loadMoreAndyInfo(nextPageUrl: String?) = Api.getDefault().getMoreAndyInfo(nextPageUrl)?.compose(RxHelper.handleResult())
}