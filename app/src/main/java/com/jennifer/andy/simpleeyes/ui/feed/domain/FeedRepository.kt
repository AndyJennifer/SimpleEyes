package com.jennifer.andy.simpleeyes.ui.feed.domain


/**
 * Author:  andy.xwt
 * Date:    2020-01-02 21:22
 * Description:
 */

class FeedRepository(private val feedRemoteDataSource: FeedRemoteDataSource) {


    /**
     * 获取发现tab栏
     */
    fun getDiscoveryTab() = feedRemoteDataSource.getDiscoveryTab()

    /**
     * 获取全部分类信息
     */
    fun loadAllCategoriesInfo() = feedRemoteDataSource.loadAllCategoriesInfo()

    /**
     * 获取排行榜tab栏
     */
    fun getRankListTab() = feedRemoteDataSource.getRankListTab()

    /**
     * 获取专题信息
     */
    fun getTopicInfo() = feedRemoteDataSource.getTopicInfo()

    /**
     * 获取种类下tab信息
     */
    fun getCategoryTabInfo(id: String) = feedRemoteDataSource.getCategoryTabInfo(id)

    /**
     * 根据url,获取数据
     */
    fun getDataFromUrl(url: String) = feedRemoteDataSource.getDataFromUrl(url)
}