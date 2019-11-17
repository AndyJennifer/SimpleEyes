package com.jennifer.andy.simpleeyes.ui.home.model

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.Api
import com.jennifer.andy.simpleeyes.rx.RxThreadHelper
import com.jennifer.andy.simpleeyes.rx.error.globalHandleError
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2017/9/19 18:00
 * Description:
 */

class HomeModel : BaseModel {

    /**
     * 加载首页信息
     */
    fun loadCategoryInfo(): Observable<AndyInfo> =
            Api.getDefault()
                    .getHomeInfo()
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())

    /**
     * 刷新主页信息，延迟1秒执行
     */
    fun refreshCategoryInfo(): Observable<AndyInfo> =
            Api.getDefault()
                    .getHomeInfo()
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())


    /**
     * 热门关键词获取
     */
    fun getHotWord(): Observable<MutableList<String>> =
            Api.getDefault()
                    .getHotWord()
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())

    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String): Observable<AndyInfo> =
            Api.getDefault()
                    .searchVideoByWord(word)
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite(): Observable<JenniferInfo> =
            Api.getDefault()
                    .getDailyElite()
                    .compose(globalHandleError())
                    .compose(RxThreadHelper.switchObservableThread())
}