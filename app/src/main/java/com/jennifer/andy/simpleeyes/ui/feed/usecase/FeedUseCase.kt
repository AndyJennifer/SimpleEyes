package com.jennifer.andy.simpleeyes.ui.feed.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.feed.domain.FeedRepository


/**
 * Author:  andy.xwt
 * Date:    2020-01-02 21:27
 * Description:
 */

class FeedUseCase(private val feedRepository: FeedRepository) {


    /**
     * 获取发现tab栏
     */
    fun getDiscoveryTab() =
            feedRepository
                    .getDiscoveryTab()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 获取全部分类信息
     */
    fun loadAllCategoriesInfo() =
            feedRepository
                    .loadAllCategoriesInfo()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 获取排行榜tab栏
     */
    fun getRankListTab() =
            feedRepository
                    .getRankListTab()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 获取专题信息
     */
    fun getTopicInfo() =
            feedRepository
                    .getTopicInfo()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 获取种类下tab信息
     */
    fun getCategoryTabIno(id: String) =
            feedRepository
                    .getCategoryTabInfo(id)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 根据url,获取数据
     */
    fun getDataFromUrl(url: String) =
            feedRepository
                    .getDataFromUrl(url)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 根据url,获取更多数据
     */
    fun loadMoreDataFromUrl(url: String) =
            feedRepository.getDataFromUrl(url)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


}