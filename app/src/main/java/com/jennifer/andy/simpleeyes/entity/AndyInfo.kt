package com.jennifer.andy.simpleeyes.entity

import java.io.Serializable

/**
 * Author:  andy.xwt
 * Date:    2017/10/19 19:00
 * Description: 页面数据信息,主要是视频信息
 */


data class AndyInfo(var count: Int, var total: Int,
                    var nextPageUrl: String?, var date: Long,
                    var nextPublishTime: Long, var dialog: String,
                    var topIssue: TopIssue, var refreshCount: Int,
                    var lastStartId: Int, var itemList: MutableList<Content>) : Serializable

data class JenniferInfo(var nextPageUrl: String?, var date: Long,
                        var nextPublishTime: Long, var dialog: String,
                        var topIssue: TopIssue, var refreshCount: Int,
                        var lastStartId: Int, var issueList: MutableList<ContentBean>) : Serializable

data class Content(var type: String,
                   var data: ContentBean,
                   var id: String,
                   var date: Long,
                   var tag: String) : Serializable


data class ContentBean(var dataType: String,
                       var header: Header,
                       var content: Content?,
                       var itemList: MutableList<Content>,
                       var id: String,
                       var title: String,
                       var description: String,
                       var library: String,
                       var tags: MutableList<TagsBean>,
                       var consumption: ConsumptionBean,
                       var image: String,
                       var resourceType: String,
                       var slogan: String,
                       var provider: ProviderBean,
                       var category: String,
                       var author: AuthorBean,
                       var cover: CoverBean,
                       var playUrl: String,
                       var thumbPlayUrl: String,
                       var duration: Int,
                       var webUrl: WebUrlBean,
                       var releaseTime: Long,
                       var playInfo: MutableList<PlayInfoBean>,
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
                       var labelList: MutableList<*>,
                       var descriptionEditor: String,
                       var isCollected: Boolean,
                       var isPlayed: Boolean,
                       var subtitles: MutableList<*>,
                       var lastViewTime: String,
                       var playlists: String,
                       var text: String,
                       var icon: String,
                       var iconType: String,
                       var height: Int,
                       var src: Int

) : Serializable


data class Header(var id: Int,
                  var title: String,
                  var subTitle: String,
                  var font: String,
                  var cover: String,
                  var label: Any,
                  var actionUrl: String,
                  var labelList: MutableList<*>,
                  var icon: String,
                  var iconType: String,
                  var description: String,
                  var time: Long) : Serializable


data class TagsBean(var id: Int,
                    var name: String,
                    var actionUrl: String,
                    var adTrack: String) : Serializable


data class ConsumptionBean(var collectionCount: String,
                           var shareCount: String,
                           var replyCount: String) : Serializable


data class TopIssue(var type: String,
                    var data: TopIssueBean,
                    var tag: String) : Serializable

data class TopIssueBean(var dataType: String,
                        var count: Int,
                        var itemList: MutableList<Content>) : Serializable


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


data class PlayInfoBean(var height: Int,
                        var width: Int,
                        var name: String,
                        var type: String,
                        var url: String,
                        var urlList: MutableList<UrlListBean>) : Serializable

data class UrlListBean(var name: String,
                       var url: String,
                       var size: Int) : Serializable



