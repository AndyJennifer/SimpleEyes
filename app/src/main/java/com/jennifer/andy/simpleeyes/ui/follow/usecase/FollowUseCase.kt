package com.jennifer.andy.simpleeyes.ui.follow.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.usecase.LoadMoreUseCase
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.follow.domain.FollowRepository


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 22:54
 * Description:
 */

class FollowUseCase(private val followRepository: FollowRepository) : LoadMoreUseCase(followRepository) {


    /**
     * 获取关注首页信息
     */
    fun getFollowInfo() =
            followRepository
                    .getFollowInfo()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


    /**
     * 获取全部作者
     */
    fun getAllAuthor() =
            followRepository
                    .getAllAuthor()
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }


}