package com.jennifer.andy.simpleeyes.ui.follow.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 22:57
 * Description:
 */

class FollowRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 获取关注首页信息
     */
    fun getFollowInfo(): Flowable<AndyInfo> = Api.getDefault().getFollowInfo()


    /**
     * 获取全部作者
     */
    fun getAllAuthor(): Flowable<AndyInfo> = Api.getDefault().getAllAuthor()

}