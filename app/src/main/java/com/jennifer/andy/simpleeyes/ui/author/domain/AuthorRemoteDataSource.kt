package com.jennifer.andy.simpleeyes.ui.author.domain

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.net.Api


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 14:28
 * Description:
 */

class AuthorRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String) = Api.getDefault().getAuthorTagDetail(id)
}