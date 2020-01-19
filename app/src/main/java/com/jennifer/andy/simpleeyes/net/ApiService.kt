package com.jennifer.andy.simpleeyes.net

import com.jennifer.andy.simpleeyes.net.entity.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Author:  andy.xwt
 * Date:    2017/10/10 22:46
 * Description:
 */

interface ApiService {

    ///////////////////////////////////////////////////////////////////////////
    // 首页相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 首页
     */
    @GET("api/v4/tabs/selected")
    fun getHomeInfo(): Flowable<AndyInfo>

    /**
     * 获取热门关键词
     */
    @GET("api/v3/queries/hot")
    fun getHotWord(): Flowable<MutableList<String>>

    /**
     * 关键词搜索
     */
    @GET("api/v1/search")
    fun searchVideoByWord(@Query("query") word: String): Flowable<AndyInfo>

    /**
     * 每日精选旁的日历显示
     */
    @GET("api/v3/issueNavigationList")
    fun getIssueNaviGationList(): Flowable<JenniferInfo>

    ///////////////////////////////////////////////////////////////////////////
    // 发现相关
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 发现
     */
    @GET("api/v4/discovery")
    fun getDiscoveryTab(): Flowable<Tab>

    /**
     * 获取全部分类信息
     */
    @GET("api/v4/categories/all")
    fun getAllCategoriesInfo(): Flowable<AndyInfo>

    /**
     * 获取排行榜tab信息
     */
    @GET("api/v4/rankList")
    fun getRankListTab(): Flowable<Tab>

    /**
     * 获取专题信息
     */
    @GET("api/v3/specialTopics")
    fun getTopicInfo(): Flowable<AndyInfo>

    /**
     * 获取tag信息
     * @param tagId tagId
     * @param strategy tag模式
     */
    @GET("api/v3/tag/videos")
    fun getTagInfo(@Query("tagId") tagId: String, @Query("strategy") strategy: String): Flowable<AndyInfo>

    /**
     * 获取种类下tab详细信息
     */
    @GET("api/v4/categories/detail/tab")
    fun getCategoryTabInfo(@Query("id") id: String): Flowable<Category>

    /**
     * 获取种类下tab下列表集合
     */
    @GET("api/v4/categories/detail/index")
    fun getCategroyTabListItemInfo(@Query("id") id: String): Flowable<AndyInfo>

    ///////////////////////////////////////////////////////////////////////////
    // 关注相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 关注
     */
    @GET("api/v4/tabs/follow")
    fun getFollowInfo(): Flowable<AndyInfo>

    /**
     * 全部作者
     */
    @GET("api/v4/pgcs/all")
    fun getAllAuthor(): Flowable<AndyInfo>

    /**
     * 作者详细信息
     */
    @GET("api/v4/pgcs/detail/tab")
    fun getAuthorTagDetail(@Query("id") id: String): Flowable<Tab>

    ///////////////////////////////////////////////////////////////////////////
    // 公共接口
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 根据url,获取数据
     * @url tab请求地址
     */
    @GET
    fun getDataFromUrl(@Url url: String): Flowable<AndyInfo>

    /**
     * 根据url,获取更多信息
     * @param url 下一页请求地址
     */
    @GET
    fun getMoreAndyInfo(@Url url: String): Flowable<AndyInfo>


    /**
     * 根据url,获取更多信息
     * @param url 下一页请求地址
     */
    @GET
    fun getMoreJenniferInfo(@Url url: String): Flowable<JenniferInfo>


    ///////////////////////////////////////////////////////////////////////////
    // 视频相关
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 根据视频id,获取相关信息
     */
    @GET("api/v2/video/{id}")
    fun getVideoInfoById(@Path("id") id: String): Flowable<ContentBean>

    /**
     * 获取相关视频信息
     * @id 视频id
     */
    @GET("api/v4/video/related")
    fun getRelatedVideo(@Query("id") id: String): Flowable<AndyInfo>

    /**
     * 每日编辑精选
     */
    @GET("api/v2/feed?num=3")
    fun getDailyElite(): Flowable<JenniferInfo>


    /**
     * 下载视频
     */
    @Streaming
    @GET
    fun downloadVideo(@Url url: String): Call<ResponseBody>


}