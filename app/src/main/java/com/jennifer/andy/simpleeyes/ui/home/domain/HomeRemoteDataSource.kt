package com.jennifer.andy.simpleeyes.ui.home.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:00
 * Description:
 */

class HomeRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 加载首页信息
     */
    fun loadCategoryInfo(): Flowable<AndyInfo> = Api.getDefault().getHomeInfo()

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite(): Flowable<JenniferInfo> = Api.getDefault().getDailyElite()

}