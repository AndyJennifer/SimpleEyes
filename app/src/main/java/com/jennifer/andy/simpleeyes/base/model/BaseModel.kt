package com.jennifer.andy.simpleeyes.base.model

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:08
 * Description:
 */

interface BaseModel {

    /**
     * 加载更多信息，针对于AndyInfo数据类型
     */
    fun loadMoreAndyInfo(nextPageUrl: String): Flowable<AndyInfo> = Api.getDefault()
            .getMoreAndyInfo(nextPageUrl)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchFlowableThread())

    /**
     * 加载更多信息，针对于JenniferInfo数据类型
     */
    fun loadMoreJenniferInfo(nextPageUrl: String): Flowable<JenniferInfo> = Api.getDefault()
            .getMoreJenniferInfo(nextPageUrl)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchFlowableThread())

    /**
     * 根据url,获取数据
     */
    fun getDataFromUrl(url: String): Flowable<AndyInfo> = Api.getDefault()
            .getDataFromUrl(url)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchFlowableThread())
}