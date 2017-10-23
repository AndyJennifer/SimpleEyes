package com.jennifer.andy.simpleeyes.net

import com.jennifer.andy.simpleeyes.entity.AndyInfo
import io.reactivex.Observable
import retrofit2.http.GET


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
     * 首页关键词搜索
     */
    @GET("api/v3/queries/hot")
    fun getCategoryHot(): Observable<AndyInfo>


    /**
     * 关注
     */
    @GET("/api/v4/tabs/follow")
    fun getFollowTabs(): Observable<AndyInfo>

    /**
     * 发现
     */
    @GET("/api/v4/tabs/discovery")
    fun getDiscoveryTab(): Observable<AndyInfo>


}