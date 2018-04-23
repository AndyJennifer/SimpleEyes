package com.jennifer.andy.simpleeyes.net

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.entity.JenniferInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Author:  andy.xwt
 * Date:    2017/10/10 22:46
 * Description:
 */

interface ApiService {


    /**
     * 首页
     */
    @GET("api/v4/tabs/selected")
    fun getCategory(): Observable<AndyInfo>

    /**
     * 获取更多信息
     * @param url 下一页请求地址
     */
    @GET
    fun getMoreInfo(@Url url: String?): Observable<AndyInfo>

    /**
     * 获取热门关键词
     */
    @GET("api/v3/queries/hot")
    fun getHotWord(): Observable<MutableList<String>>

    /**
     * 关键词搜索
     */
    @GET("api/v1/search")
    fun searchVideoByWord(@Query("query") word: String): Observable<AndyInfo>

    /**
     * 关注
     */
    @GET("api/v4/tabs/follow")
    fun getFollowTabs(): Observable<AndyInfo>

    /**
     * 发现
     */
    @GET("api/v4/tabs/discovery")
    fun getDiscoveryTab(): Observable<AndyInfo>

    /**
     * 获取相关视频信息
     */
    @GET("api/v4/video/related")
    fun getRelatedVideo(@Query("id") id: String): Observable<AndyInfo>

    /**
     * 每日编辑精选
     */
    @GET("api/v2/feed?num=3")
    fun getDailyElite(): Observable<JenniferInfo>

    /**
     * 每日精选旁的日历显示
     */
    @GET("api/v3/issueNavigationList")
    fun getIssueNaviGationList(): Observable<JenniferInfo>


}