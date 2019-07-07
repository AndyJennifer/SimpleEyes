package com.jennifer.andy.simpleeyes.ui.follow.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:54
 * Description:
 */

class FollowModel : BaseModel {


    /**
     * 获取关注首页信息
     */
    fun getFollowInfo(): Observable<AndyInfo> = Api.getDefault().getFollowInfo().compose(RxHelper.handleResult())
}