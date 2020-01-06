package com.jennifer.andy.simpleeyes.ui.home.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.usecase.LoadMoreUseCase
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.home.domain.HomeRepository

/**
 * Author:  andy.xwt
 * Date:    2019-12-08 19:33
 * Description:
 */
class HomeUseCase(private val homeRepository: HomeRepository) : LoadMoreUseCase(homeRepository) {


    /**
     * 加载首页信息
     */
    fun loadCategoryData() =
            homeRepository
                    .loadCategoryData()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String) =
            homeRepository.searchVideoByWord(word)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite() =
            homeRepository.getDailyElite()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


}
