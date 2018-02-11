package com.jennifer.andy.simpleeyes.entity

import java.io.Serializable

/**
 * Author:  andy.xwt
 * Date:    2017/10/19 19:00
 * Description: 页面数据信息
 */


data class AndyInfo(var count: Int, var total: Int,
                    var nextPageUrl: String?, var date: Long,
                    var nextPublishTime: Long, var dialog: String,
                    var topIssue: TopIssueBean, var refreshCount: Int,
                    var lastStartId: Int, var itemList: MutableList<ItemListBean>) : Serializable


data class TopIssueBean(var type: String,
                        var data: DataBeanX,
                        var tag: String) : Serializable

data class DataBeanX(var dataType: String,
                     var count: Int,
                     var itemList: MutableList<ItemListBean>) : Serializable


data class ItemListBean(var type: String,
                        var data: DataBeanXX,
                        var tag: String) : Serializable

data class HeaderBean(var id: Int,
                      var title: String,
                      var font: String,
                      var cover: String,
                      var label: Any,
                      var actionUrl: String,
                      var labelList: MutableList<*>,
                      var icon: String,
                      var iconType: String,
                      var description: String,
                      var time: Long) : Serializable

data class ContentBean(var type: String,
                       var data: DataBeanXX,
                       var tag: String) : Serializable

data class DataBeanXX(var dataType: String,
                      var id: String,
                      var title: String,
                      var slogan: String,
                      var description: String,
                      var provider: ProviderBean,
                      var category: String,
                      var author: AuthorBean,
                      var cover: CoverBean,
                      var playUrl: String,
                      var thumbPlayUrl: String,
                      var duration: Int,
                      var webUrl: WebUrlBean,
                      var releaseTime: Long,
                      var library: String,
                      var consumption: ConsumptionBean,
                      var campaign: String,
                      var waterMarks: String,
                      var type: String,
                      var titlePgc: String,
                      var descriptionPgc: String,
                      var remark: String,
                      var idx: Int,
                      var shareAdTrack: String,
                      var favoriteAdTrack: String,
                      var webAdTrack: String,
                      var date: Long,
                      var label: Any,
                      var descriptionEditor: String,
                      var isCollected: Boolean,
                      var isPlayed: Boolean,
                      var lastViewTime: String,
                      var playlists: String,
                      var playInfo: MutableList<PlayInfoBean>,
                      var tags: MutableList<TagsBean>,
                      var itemList: MutableList<ItemListBean>,
                      var labelList: MutableList<*>,
                      var header: HeaderBean,
                      var image: String,
                      var text: String,
                      var content: ContentBean,
                      var subtitles: MutableList<*>) : Serializable


data class ProviderBean(var name: String,
                        var alias: String,
                        var icon: String) : Serializable

data class AuthorBean(var id: Int,
                      var icon: String,
                      var name: String,
                      var description: String,
                      var link: String,
                      var latestReleaseTime: Long,
                      var videoNum: Int,
                      var adTrack: String,
                      var follow: FollowBean,
                      var shield: ShieldBean,
                      var approvedNotReadyVideoCount: Int,
                      var isIfPgc: Boolean = false) : Serializable


data class FollowBean(
        var itemType: String,
        var itemId: Int,
        var isFollowed: Boolean) : Serializable

data class ShieldBean(
        var itemType: String,
        var itemId: Int,
        var isShielded: Boolean) : Serializable


data class CoverBean(var feed: String,
                     var detail: String,
                     var blurred: String,
                     var sharing: String,
                     var homepage: String) : Serializable


data class WebUrlBean(var raw: String,
                      var forWeibo: String) : Serializable

data class ConsumptionBean(var collectionCount: Int,
                           var shareCount: Int,
                           var replyCount: Int) : Serializable

data class PlayInfoBean(var height: Int,
                        var width: Int,
                        var name: String,
                        var type: String,
                        var url: String,
                        var urlList: MutableList<UrlListBean>) : Serializable

data class UrlListBean(var name: String,
                       var url: String,
                       var size: Int) : Serializable


data class TagsBean(var id: Int,
                    var name: String,
                    var actionUrl: String,
                    var adTrack: String) : Serializable



