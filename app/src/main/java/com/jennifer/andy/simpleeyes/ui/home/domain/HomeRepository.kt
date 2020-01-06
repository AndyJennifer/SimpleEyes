package com.jennifer.andy.simpleeyes.ui.home.domain

import com.jennifer.andy.simpleeyes.base.domain.LoadMoreRepository


/**
 * Author:  andy.xwt
 * Date:    2019-11-29 16:30
 * Description:
 */

class HomeRepository(private val homeRemoteDataSource: HomeRemoteDataSource)
    : LoadMoreRepository(homeRemoteDataSource) {


    /**
     * 加载首页信息
     */
    fun loadCategoryData() = homeRemoteDataSource.loadCategoryInfo()

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String) = homeRemoteDataSource.searchVideoByWord(word)

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite() = homeRemoteDataSource.getDailyElite()
}
