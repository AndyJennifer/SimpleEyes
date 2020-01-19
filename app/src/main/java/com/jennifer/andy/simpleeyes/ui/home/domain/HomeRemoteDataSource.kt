package com.jennifer.andy.simpleeyes.ui.home.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.Api
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
     * 热门关键词获取
     */
    fun getHotWord(): Flowable<MutableList<String>> = Api.getDefault().getHotWord()


    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String): Flowable<AndyInfo> = Api.getDefault().searchVideoByWord(word)


    /**
     * 获取每日编辑精选
     */
    fun getDailyElite(): Flowable<JenniferInfo> = Api.getDefault().getDailyElite()

}