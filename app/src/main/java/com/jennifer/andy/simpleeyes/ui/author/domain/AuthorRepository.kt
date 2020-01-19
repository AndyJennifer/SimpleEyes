package com.jennifer.andy.simpleeyes.ui.author.domain


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 14:29
 * Description:
 */

class AuthorRepository(private val authorRemoteDataSource: AuthorRemoteDataSource) {


    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String) = authorRemoteDataSource.getAuthorTagDetail(id)
}