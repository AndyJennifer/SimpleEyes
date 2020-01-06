package com.jennifer.andy.simpleeyes.ui.author.model

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.model.BaseModel
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:34
 * Description:
 */

class AuthorModel : BaseModel {

    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String): Flowable<Tab> =
            Api.getDefault()
                    .getAuthorTagDetail(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchFlowableThread())

}