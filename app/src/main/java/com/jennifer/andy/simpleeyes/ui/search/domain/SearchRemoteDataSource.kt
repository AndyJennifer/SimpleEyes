package com.jennifer.andy.simpleeyes.ui.search.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:07
 * Description:
 */

class SearchRemoteDataSource : BaseRemoteDataSource() {


    /**
     * 获取热门关键词
     */
    fun getHotWord(): Flowable<MutableList<String>> = Api.getDefault().getHotWord()

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String): Flowable<AndyInfo> = Api.getDefault().searchVideoByWord(word)


}