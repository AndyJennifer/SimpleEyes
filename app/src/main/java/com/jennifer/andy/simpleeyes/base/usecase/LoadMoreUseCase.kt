package com.jennifer.andy.simpleeyes.base.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.domain.LoadMoreRepository
import com.jennifer.andy.simpleeyes.net.result.Result


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 23:10
 * Description:
 */

open class LoadMoreUseCase(private val loadMoreRepository: LoadMoreRepository) {


    /**
     * 获取更多信息
     */
    fun loadMoreJenniferInfo(nextPageUrl: String) =
            loadMoreRepository.loadMoreJenniferInfo(nextPageUrl)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 获取更多信息
     */
    fun loadMoreAndyInfo(nextPageUrl: String) =
            loadMoreRepository.loadMoreAndyInfo(nextPageUrl)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 获取更多信息
     */
    fun getDataFromUrl(nextPageUrl: String) =
            loadMoreRepository.getDataFromUrl(nextPageUrl)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

}