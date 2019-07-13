package com.jennifer.andy.simpleeyes.ui.author.model

import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:34
 * Description:
 */

class AuthorModel : BaseModel {

    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String) = Api.getDefault().getAuthorTagDetail(id).compose(RxHelper.handleResult())

}