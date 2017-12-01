package com.jennifer.andy.simpleeyes.entity

/**
 * Author:  andy.xwt
 * Date:    2017/10/19 19:00
 * Description: 页面数据信息
 */


data class AndyInfo(var count: Int, var total: Int,
                    var nextPageUrl: String, var date: Long,
                    var nextPublishTime: Long, var dialog: Any,
                    var topIssue: TopIssueBean, var refreshCount: Int,
                    var lastStartId: Int, var itemList: MutableList<ItemListBean>)


data class TopIssueBean(var type: String,
                        var data: DataBeanX,
                        var tag: Any)

data class DataBeanX(var dataType: String,
                     var count: Int,
                     var itemList: MutableList<ItemListBean>)


data class ItemListBean(var type: String,
                        var data: DataBeanXX,
                        var tag: Any)

data class HeaderBean(var id: Int,
                      var title: String,
                      var font: Any,
                      var cover: String,
                      var label: Any,
                      var actionUrl: String,
                      var labelList: Any,
                      var icon: String,
                      var iconType: String,
                      var description: String,
                      var time: Long)

data class ContentBean(var type: String,
                       var data: DataBeanXX,
                       var tag: Any)

data class DataBeanXX(var dataType: String,
                      var id: Int,
                      var title: String,
                      var slogan: String,
                      var description: String,
                      var provider: ProviderBean,
                      var category: String,
                      var author: AuthorBean,
                      var cover: CoverBean,
                      var playUrl: String,
                      var thumbPlayUrl: Any,
                      var duration: Int,
                      var webUrl: WebUrlBean,
                      var releaseTime: Long,
                      var library: String,
                      var consumption: ConsumptionBean,
                      var campaign: Any,
                      var waterMarks: Any,
                      var adTrack: Any,
                      var type: String,
                      var titlePgc: Any,
                      var descriptionPgc: Any,
                      var remark: Any,
                      var idx: Int,
                      var shareAdTrack: Any,
                      var favoriteAdTrack: Any,
                      var webAdTrack: Any,
                      var date: Long,
                      var promotion: Any,
                      var label: Any,
                      var descriptionEditor: String,
                      var isCollected: Boolean,
                      var isPlayed: Boolean,
                      var lastViewTime: Any,
                      var playlists: Any,
                      var playInfo: MutableList<PlayInfoBean>,
                      var tags: MutableList<TagsBean>,
                      var itemList: MutableList<ItemListBean>,
                      var labelList: MutableList<*>,
                      var header: HeaderBean,
                      var image: String,
                      var text: String,
                      var content: ContentBean,
                      var subtitles: MutableList<*>)


data class ProviderBean(var name: String,
                        var alias: String,
                        var icon: String)

data class AuthorBean(var id: Int,
                      var icon: String,
                      var name: String,
                      var description: String,
                      var link: String,
                      var latestReleaseTime: Long,
                      var videoNum: Int,
                      var adTrack: Any,
                      var follow: FollowBean,
                      var shield: ShieldBean,
                      var approvedNotReadyVideoCount: Int,
                      var isIfPgc: Boolean = false)


data class FollowBean(
        var itemType: String,
        var itemId: Int,
        var isFollowed: Boolean)

data class ShieldBean(
        var itemType: String,
        var itemId: Int,
        var isShielded: Boolean)


data class CoverBean(var feed: String,
                     var detail: String,
                     var blurred: String,
                     var sharing: Any,
                     var homepage: Any)


data class WebUrlBean(var raw: String,
                      var forWeibo: String)

data class ConsumptionBean(var collectionCount: Int,
                           var shareCount: Int,
                           var replyCount: Int)

data class PlayInfoBean(var height: Int,
                        var width: Int,
                        var name: String,
                        var type: String,
                        var url: String,
                        var urlList: MutableList<UrlListBean>)

data class UrlListBean(var name: String,
                       var url: String,
                       var size: Int)


data class TagsBean(var id: Int,
                    var name: String,
                    var actionUrl: String,
                    var adTrack: Any)



