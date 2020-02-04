package com.jennifer.andy.simpleeyes.ui.search.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.usecase.LoadMoreUseCase
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.search.domain.SearchRepository


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:05
 * Description:
 */

class SearchUseCase(private val searchRepository: SearchRepository) : LoadMoreUseCase(searchRepository) {


    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String) =
            searchRepository.searchVideoByWord(word)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 获取热门关键词
     */
    fun getHotWord() =
            searchRepository.getHotWord()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

}