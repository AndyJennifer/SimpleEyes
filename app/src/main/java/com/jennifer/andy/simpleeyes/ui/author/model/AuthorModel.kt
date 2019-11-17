package com.jennifer.andy.simpleeyes.ui.author.model

import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxThreadHelper
import com.jennifer.andy.simpleeyes.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2019-07-13 22:34
 * Description:
 */

class AuthorModel : BaseModel {

    /**
     * 获取作者
     */
    fun getAuthorTagDetail(id: String): Observable<Tab> =
            Api.getDefault()
                    .getAuthorTagDetail(id)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())

}