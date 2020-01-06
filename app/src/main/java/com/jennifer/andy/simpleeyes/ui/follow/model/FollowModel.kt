package com.jennifer.andy.simpleeyes.ui.follow.model

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.base.model.BaseModel
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Api
import io.reactivex.Flowable


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

class FollowModel : BaseModel {


    /**
     * 获取关注首页信息
     */
    fun getFollowInfo(): Flowable<AndyInfo> =
            Api.getDefault()
                    .getFollowInfo()
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchFlowableThread())


    /**
     * 获取全部作者
     */
    fun getAllAuthor(): Flowable<AndyInfo> =
            Api.getDefault()
                    .getAllAuthor()
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchFlowableThread())
}