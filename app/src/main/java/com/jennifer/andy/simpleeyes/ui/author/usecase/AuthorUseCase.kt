package com.jennifer.andy.simpleeyes.ui.author.usecase

import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.author.domain.AuthorRepository


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 14:29
 * Description:
 */

class AuthorUseCase(private val authorRepository: AuthorRepository) {


    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String) =
            authorRepository
                    .getAuthorTagDetail(id)
                    .compose(globalHandleError())
                    .map { Result.success(it) }
                    .onErrorReturn { Result.error(it) }

}