package com.jennifer.andy.simpleeyes.ui.search.domain

import com.jennifer.andy.simpleeyes.base.domain.LoadMoreRepository


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:06
 * Description:
 */

class SearchRepository(private val searchRemoteDataSource: SearchRemoteDataSource)
    : LoadMoreRepository(searchRemoteDataSource) {

    /**
     * 获取热门关键词
     */
    fun getHotWord() = searchRemoteDataSource.getHotWord()

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String) = searchRemoteDataSource.searchVideoByWord(word)
}