package com.jennifer.andy.simpleeyes.ui.follow.domain

import com.jennifer.andy.simpleeyes.base.domain.LoadMoreRepository
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 22:54
 * Description:
 */

class FollowRepository(private val followRemoteDataSource: FollowRemoteDataSource) : LoadMoreRepository(followRemoteDataSource) {


    /**
     * 获取关注首页信息
     */
    fun getFollowInfo(): Flowable<AndyInfo> = followRemoteDataSource.getFollowInfo()


    /**
     * 获取全部作者
     */
    fun getAllAuthor(): Flowable<AndyInfo> = followRemoteDataSource.getAllAuthor()
}