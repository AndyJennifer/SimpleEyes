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
                    var lastStartId: Int, var itemList: MutableList<ItemListBeanX>) {


    data class TopIssueBean(var type: String,
                            var data: DataBeanX,
                            var tag: Any) {

        data class DataBeanX(var dataType: String,
                             var count: Int,
                             var itemList: MutableList<ItemListBean>) {

            data class ItemListBean(var type: String,
                                    var data: DataBean,
                                    var tag: Any) {

                class DataBean {
                    var dataType: String? = null
                    var id: Int = 0
                    var title: String? = null
                    var slogan: String? = null
                    var description: String? = null
                    var provider: ProviderBean? = null
                    var category: String? = null
                    var author: Any? = null
                    var cover: CoverBean? = null
                    var playUrl: String? = null
                    var thumbPlayUrl: Any? = null
                    var duration: Int = 0
                    var webUrl: WebUrlBean? = null
                    var releaseTime: Long = 0
                    var library: String? = null
                    var consumption: ConsumptionBean? = null
                    var campaign: Any? = null
                    var waterMarks: Any? = null
                    var adTrack: Any? = null
                    var type: String? = null
                    var titlePgc: Any? = null
                    var descriptionPgc: Any? = null
                    var remark: String? = null
                    var idx: Int = 0
                    var shareAdTrack: Any? = null
                    var favoriteAdTrack: Any? = null
                    var webAdTrack: Any? = null
                    var date: Long = 0
                    var promotion: Any? = null
                    var label: Any? = null
                    var descriptionEditor: String? = null
                    var isCollected: Boolean = false
                    var isPlayed: Boolean = false
                    var lastViewTime: Any? = null
                    var playlists: Any? = null
                    var playInfo: MutableList<PlayInfoBean>? = null
                    var tags: MutableList<TagsBean>? = null
                    var labelList: MutableList<*>? = null
                    var subtitles: MutableList<*>? = null

                    class ProviderBean {

                        var name: String? = null
                        var alias: String? = null
                        var icon: String? = null
                    }

                    class CoverBean {

                        var feed: String? = null
                        var detail: String? = null
                        var blurred: String? = null
                        var sharing: Any? = null
                        var homepage: String? = null
                    }

                    class WebUrlBean {

                        var raw: String? = null
                        var forWeibo: String? = null
                    }

                    class ConsumptionBean {

                        var collectionCount: Int = 0
                        var shareCount: Int = 0
                        var replyCount: Int = 0
                    }

                    class PlayInfoBean {


                        var height: Int = 0
                        var width: Int = 0
                        var name: String? = null
                        var type: String? = null
                        var url: String? = null
                        var urlList: MutableList<UrlListBean>? = null

                        class UrlListBean {


                            var name: String? = null
                            var url: String? = null
                            var size: Int = 0
                        }
                    }

                    class TagsBean {


                        var id: Int = 0
                        var name: String? = null
                        var actionUrl: String? = null
                        var adTrack: Any? = null
                    }
                }
            }
        }
    }

    data class ItemListBeanX(var type: String,
                             var data: DataBeanXXX,
                             var tag: Any) {


        data class DataBeanXXX(var dataType: String,
                               var header: HeaderBean,
                               var content: ContentBean,
                               var itemList: MutableList<ItemListData>,
                               var adTrack: Any) {

            data class ItemListData(var type: String,
                                    var data: Item,
                                    var tag: Any) {

                data class Item(var dataType: String,
                                var id: Int,
                                var title: String,
                                var description: String,
                                var image: String,
                                var actionUrl: String,
                                var text: String,
                                var adTrack: Any,
                                var shade: Boolean,
                                var library: String,
                                var cover: ContentBean.DataBeanXX.CoverBeanX
                )
            }

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
                                   var tag: Any) {

                data class DataBeanXX(var dataType: String,
                                      var id: Int,
                                      var title: String,
                                      var slogan: Any,
                                      var description: String,
                                      var provider: ProviderBeanX,
                                      var category: String,
                                      var author: AuthorBean,
                                      var cover: CoverBeanX,
                                      var playUrl: String,
                                      var thumbPlayUrl: Any,
                                      var duration: Int,
                                      var webUrl: WebUrlBeanX,
                                      var releaseTime: Long,
                                      var library: String,
                                      var consumption: ConsumptionBeanX,
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
                                      var playInfo: MutableList<PlayInfoBeanX>,
                                      var tags: MutableList<TagsBeanX>,
                                      var labelList: MutableList<*>,
                                      var subtitles: MutableList<*>) {


                    data class ProviderBeanX(var name: String,
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
                                          var isIfPgc: Boolean = false) {


                        data class FollowBean(
                                var itemType: String,
                                var itemId: Int,
                                var isFollowed: Boolean)

                        data class ShieldBean(
                                var itemType: String,
                                var itemId: Int,
                                var isShielded: Boolean)
                    }

                    data class CoverBeanX(var feed: String,
                                          var detail: String,
                                          var blurred: String,
                                          var sharing: Any,
                                          var homepage: Any)


                    data class WebUrlBeanX(var raw: String,
                                           var forWeibo: String)

                    data class ConsumptionBeanX(var collectionCount: Int,
                                                var shareCount: Int,
                                                var replyCount: Int)

                    data class PlayInfoBeanX(var height: Int,
                                             var width: Int,
                                             var name: String,
                                             var type: String,
                                             var url: String,
                                             var urlList: MutableList<UrlListBeanX>) {

                        data class UrlListBeanX(var name: String,
                                                var url: String,
                                                var size: Int)

                    }

                    data class TagsBeanX(var id: Int,
                                         var name: String,
                                         var actionUrl: String,
                                         var adTrack: Any)
                }
            }
        }
    }
}
