package com.jennifer.andy.simpleeyes.ui.feed.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.Category
import com.jennifer.andy.simpleeyes.net.entity.Tab
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-01-02 21:22
 * Description:
 */

class FeedRemoteDataSource : BaseRemoteDataSource() {


    /**
     * 获取发现tab栏
     */
    fun getDiscoveryTab(): Flowable<Tab> = Api.getDefault().getDiscoveryTab()

    /**
     * 获取全部分类信息
     */
    fun loadAllCategoriesInfo(): Flowable<AndyInfo> = Api.getDefault().getAllCategoriesInfo()

    /**
     * 获取排行榜tab栏
     */
    fun getRankListTab(): Flowable<Tab> = Api.getDefault().getRankListTab()

    /**
     * 获取专题信息
     */
    fun getTopicInfo(): Flowable<AndyInfo> = Api.getDefault().getTopicInfo()

    /**
     * 获取种类下tab信息
     */
    fun getCategoryTabInfo(id: String): Flowable<Category> = Api.getDefault().getCategoryTabInfo(id)

}