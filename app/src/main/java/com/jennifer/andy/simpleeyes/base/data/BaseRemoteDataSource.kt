package com.jennifer.andy.simpleeyes.base.data

import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2019-12-08 20:11
 * Description:
 */

open class BaseRemoteDataSource {


    /**
     * 加载更多信息，针对于AndyInfo数据类型
     */
    fun loadMoreAndyInfo(nextPageUrl: String): Flowable<AndyInfo> =
            Api.getDefault().getMoreAndyInfo(nextPageUrl)


    /**
     * 加载更多信息，针对于JenniferInfo数据类型
     */
    fun loadMoreJenniferInfo(nextPageUrl: String): Flowable<JenniferInfo> =
            Api.getDefault().getMoreJenniferInfo(nextPageUrl)


    /**
     * 根据url,获取数据
     */
    fun getDataFromUrl(url: String): Flowable<AndyInfo> =
            Api.getDefault().getDataFromUrl(url)

}