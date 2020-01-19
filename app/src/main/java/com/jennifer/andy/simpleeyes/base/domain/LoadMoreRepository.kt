package com.jennifer.andy.simpleeyes.base.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 23:14
 * Description:
 */

open class LoadMoreRepository(private val baseRemoteDataSource: BaseRemoteDataSource) {


    /**
     * 加载更多信息，针对于AndyInfo数据类型
     */
    fun loadMoreAndyInfo(nextPageUrl: String): Flowable<AndyInfo> =
            baseRemoteDataSource.loadMoreAndyInfo(nextPageUrl)


    /**
     * 加载更多信息，针对于JenniferInfo数据类型
     */
    fun loadMoreJenniferInfo(nextPageUrl: String): Flowable<JenniferInfo> =
            baseRemoteDataSource.loadMoreJenniferInfo(nextPageUrl)


    /**
     * 根据url,获取数据
     */
    fun getDataFromUrl(url: String): Flowable<AndyInfo> =
            baseRemoteDataSource.getDataFromUrl(url)
}