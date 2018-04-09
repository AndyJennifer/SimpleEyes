package com.jennifer.andy.simpleeyes.ui.category.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxHelper
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:00
 * Description:
 */

class AndyModel : BaseModel {

    /**
     * 加载首页信息
     */
    fun loadCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getCategory().compose(RxHelper.handleResult())

    /**
     * 刷新主页信息，延迟1秒执行
     */
    fun refreshCategoryInfo(): Observable<AndyInfo> = Api.getDefault().getCategory().delay(1000, TimeUnit.MILLISECONDS).compose(RxHelper.handleResult())

    /**
     * 加载更多信息
     */
    fun loadMoreInfo(nextPageUrl: String?) = Api.getDefault().getMoreInfo(nextPageUrl).compose(RxHelper.handleResult())


    /**
     * 热门关键词获取
     */
    fun getHotWord(): Observable<MutableList<String>> = Api.getDefault().getHotWord().compose(RxHelper.handleResult())

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWrod(word: String): Observable<AndyInfo> = Api.getDefault().searchVideoByWord(word).compose(RxHelper.handleResult())
}