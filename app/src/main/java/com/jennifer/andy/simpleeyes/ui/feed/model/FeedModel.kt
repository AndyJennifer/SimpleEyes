package com.jennifer.andy.simpleeyes.ui.feed.model

import com.jennifer.andy.simpleeyes.entity.TabInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable


/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:52
 * Description:
 */

class FeedModel : BaseModel {

    /**
     * 获取发现tab栏
     */
    fun getDiscoveryTab(): Observable<TabInfo> = Api.getDefault().getDiscoveryTab().compose(RxHelper.handleResult())

}