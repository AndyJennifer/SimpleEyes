package com.jennifer.andy.simpleeyes.ui.base.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxThreadHelper
import com.jennifer.andy.simpleeyes.rx.error.globalHandleError
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:08
 * Description:
 */

interface BaseModel {

    /**
     * 加载更多信息，针对于AndyInfo数据类型
     */
    fun loadMoreAndyInfo(nextPageUrl: String?): Observable<AndyInfo> = Api.getDefault()
            .getMoreAndyInfo(nextPageUrl)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchObservableThread())

    /**
     * 加载更多信息，针对于JenniferInfo数据类型
     */
    fun loadMoreJenniferInfo(nextPageUrl: String?): Observable<JenniferInfo> = Api.getDefault()
            .getMoreJenniferInfo(nextPageUrl)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchObservableThread())

    /**
     * 根据url,获取数据
     */
    fun getDataInfoFromUrl(url: String?): Observable<AndyInfo> = Api.getDefault()
            .getDataInfoFromUrl(url)
            .compose(globalHandleError())
            .compose(RxThreadHelper.switchObservableThread())
}