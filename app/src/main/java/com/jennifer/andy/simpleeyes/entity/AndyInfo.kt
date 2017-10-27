package com.jennifer.andy.simpleeyes.entity

/**
 * Author:  andy.xwt
 * Date:    2017/10/19 19:00
 * Description: 页面数据信息
 */


class AndyInfo {

    var count: Int = 0
    var total: Int = 0
    var nextPageUrl: String? = null
    var date: Long = 0
    var nextPublishTime: Long = 0
    var dialog: Any? = null
    var topIssue: TopIssueBean? = null
    var refreshCount: Int = 0
    var lastStartId: Int = 0
    var itemList: MutableList<ItemListBeanX>? = null

    class TopIssueBean {
        var type: String? = null
        var data: DataBeanX? = null
        var tag: Any? = null

        class DataBeanX {
         

            var dataType: String? = null
            var count: Int = 0
            var itemList: MutableList<ItemListBean>? = null

            class ItemListBean {
               
                var type: String? = null
                var data: DataBean? = null
                var tag: Any? = null

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
                        /**
                         * name : YouTube
                         * alias : youtube
                         * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                         */

                        var name: String? = null
                        var alias: String? = null
                        var icon: String? = null
                    }

                    class CoverBean {
                        /**
                         * feed : http://img.kaiyanapp.com/f064c00a2a23e5ee3ff71a4fb74e033d.jpeg?imageMogr2/quality/60/format/jpg
                         * detail : http://img.kaiyanapp.com/f064c00a2a23e5ee3ff71a4fb74e033d.jpeg?imageMogr2/quality/60/format/jpg
                         * blurred : http://img.kaiyanapp.com/6957206b9be09817e1dee077e56350a5.jpeg?imageMogr2/quality/60/format/jpg
                         * sharing : null
                         * homepage : http://img.kaiyanapp.com/f064c00a2a23e5ee3ff71a4fb74e033d.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                         */

                        var feed: String? = null
                        var detail: String? = null
                        var blurred: String? = null
                        var sharing: Any? = null
                        var homepage: String? = null
                    }

                    class WebUrlBean {
                        /**
                         * raw : http://www.eyepetizer.net/detail.html?vid=57032
                         * forWeibo : http://www.eyepetizer.net/detail.html?vid=57032&utm_campaign=routine&utm_medium=share&utm_source=weibo
                         */

                        var raw: String? = null
                        var forWeibo: String? = null
                    }

                    class ConsumptionBean {
                        /**
                         * collectionCount : 276
                         * shareCount : 331
                         * replyCount : 6
                         */

                        var collectionCount: Int = 0
                        var shareCount: Int = 0
                        var replyCount: Int = 0
                    }

                    class PlayInfoBean {
                        /**
                         * height : 270
                         * width : 480
                         * urlList : [{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=57032&editionType=low&source=qcloud","size":15607084},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=57032&editionType=low&source=ucloud","size":15607084}]
                         * name : 流畅
                         * type : low
                         * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=57032&editionType=low&source=qcloud
                         */

                        var height: Int = 0
                        var width: Int = 0
                        var name: String? = null
                        var type: String? = null
                        var url: String? = null
                        var urlList: MutableList<UrlListBean>? = null

                        class UrlListBean {
                            /**
                             * name : qcloud
                             * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=57032&editionType=low&source=qcloud
                             * size : 15607084
                             */

                            var name: String? = null
                            var url: String? = null
                            var size: Int = 0
                        }
                    }

                    class TagsBean {
                        /**
                         * id : 4
                         * name : 运动
                         * actionUrl : eyepetizer://tag/4/?title=%E8%BF%90%E5%8A%A8
                         * adTrack : null
                         */

                        var id: Int = 0
                        var name: String? = null
                        var actionUrl: String? = null
                        var adTrack: Any? = null
                    }
                }
            }
        }
    }

    class ItemListBeanX {
        /**
         * type : followCard
         * data : {"dataType":"FollowCard","header":{"id":2508,"title":"韵律中国 2015","font":null,"cover":null,"label":null,"actionUrl":"eyepetizer://pgc/detail/602/?title=%E4%B8%AD%E5%9B%BD%E5%BB%B6%E6%97%B6%E6%91%84%E5%BD%B1%E8%81%94%E7%9B%9F&userType=PGC&tabIndex=0","labelList":null,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","iconType":"round","description":"中国延时摄影联盟  /  #旅行  /  08' 19\"","time":1443628800000},"content":{"type":"video","data":{"dataType":"VideoBeanForClient","id":2508,"title":"韵律中国 2015","slogan":null,"description":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"旅行","author":{"id":602,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","name":"中国延时摄影联盟","description":"中国最大的延时摄影社群，策划制作《韵动中国》系列延时摄影影片。公众微信：TimelapseChina","link":"","latestReleaseTime":1489772311000,"videoNum":2,"adTrack":null,"follow":{"itemType":"author","itemId":602,"followed":false},"shield":{"itemType":"author","itemId":602,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true},"cover":{"feed":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","detail":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","blurred":"http://img.kaiyanapp.com/893c145d9e156953195ec2c3e6e300f2.jpeg?imageMogr2/quality/100","sharing":null,"homepage":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=default&source=qcloud","thumbPlayUrl":null,"duration":499,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=2508","forWeibo":"http://wandou.im/nfs3k"},"releaseTime":1443628800000,"library":"DAILY","playInfo":[{"height":360,"width":640,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud","size":58928742},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=ucloud","size":58928742}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud"},{"height":480,"width":854,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud","size":86818871},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=ucloud","size":86818871}],"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud"},{"height":720,"width":1280,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud","size":102044542},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=ucloud","size":102044542}],"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud"}],"consumption":{"collectionCount":15809,"shareCount":24388,"replyCount":103},"campaign":null,"waterMarks":null,"adTrack":null,"tags":[{"id":52,"name":"风光摄影","actionUrl":"eyepetizer://tag/52/?title=%E9%A3%8E%E5%85%89%E6%91%84%E5%BD%B1","adTrack":null},{"id":342,"name":"中国","actionUrl":"eyepetizer://tag/342/?title=%E4%B8%AD%E5%9B%BD","adTrack":null},{"id":78,"name":"延时","actionUrl":"eyepetizer://tag/78/?title=%E5%BB%B6%E6%97%B6","adTrack":null}],"type":"NORMAL","titlePgc":null,"descriptionPgc":null,"remark":null,"idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1443628800000,"promotion":null,"label":null,"labelList":[],"descriptionEditor":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","collected":false,"played":false,"subtitles":[],"lastViewTime":null,"playlists":null},"tag":null},"adTrack":null}
         * tag : null
         */

        var type: String? = null
        var data: DataBeanXXX? = null
        var tag: Any? = null

        class DataBeanXXX {
            /**
             * dataType : FollowCard
             * header : {"id":2508,"title":"韵律中国 2015","font":null,"cover":null,"label":null,"actionUrl":"eyepetizer://pgc/detail/602/?title=%E4%B8%AD%E5%9B%BD%E5%BB%B6%E6%97%B6%E6%91%84%E5%BD%B1%E8%81%94%E7%9B%9F&userType=PGC&tabIndex=0","labelList":null,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","iconType":"round","description":"中国延时摄影联盟  /  #旅行  /  08' 19\"","time":1443628800000}
             * content : {"type":"video","data":{"dataType":"VideoBeanForClient","id":2508,"title":"韵律中国 2015","slogan":null,"description":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"旅行","author":{"id":602,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","name":"中国延时摄影联盟","description":"中国最大的延时摄影社群，策划制作《韵动中国》系列延时摄影影片。公众微信：TimelapseChina","link":"","latestReleaseTime":1489772311000,"videoNum":2,"adTrack":null,"follow":{"itemType":"author","itemId":602,"followed":false},"shield":{"itemType":"author","itemId":602,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true},"cover":{"feed":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","detail":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","blurred":"http://img.kaiyanapp.com/893c145d9e156953195ec2c3e6e300f2.jpeg?imageMogr2/quality/100","sharing":null,"homepage":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=default&source=qcloud","thumbPlayUrl":null,"duration":499,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=2508","forWeibo":"http://wandou.im/nfs3k"},"releaseTime":1443628800000,"library":"DAILY","playInfo":[{"height":360,"width":640,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud","size":58928742},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=ucloud","size":58928742}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud"},{"height":480,"width":854,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud","size":86818871},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=ucloud","size":86818871}],"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud"},{"height":720,"width":1280,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud","size":102044542},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=ucloud","size":102044542}],"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud"}],"consumption":{"collectionCount":15809,"shareCount":24388,"replyCount":103},"campaign":null,"waterMarks":null,"adTrack":null,"tags":[{"id":52,"name":"风光摄影","actionUrl":"eyepetizer://tag/52/?title=%E9%A3%8E%E5%85%89%E6%91%84%E5%BD%B1","adTrack":null},{"id":342,"name":"中国","actionUrl":"eyepetizer://tag/342/?title=%E4%B8%AD%E5%9B%BD","adTrack":null},{"id":78,"name":"延时","actionUrl":"eyepetizer://tag/78/?title=%E5%BB%B6%E6%97%B6","adTrack":null}],"type":"NORMAL","titlePgc":null,"descriptionPgc":null,"remark":null,"idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1443628800000,"promotion":null,"label":null,"labelList":[],"descriptionEditor":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","collected":false,"played":false,"subtitles":[],"lastViewTime":null,"playlists":null},"tag":null}
             * adTrack : null
             */

            var dataType: String? = null
            var header: HeaderBean? = null
            var content: ContentBean? = null
            var adTrack: Any? = null

            class HeaderBean {
                /**
                 * id : 2508
                 * title : 韵律中国 2015
                 * font : null
                 * cover : null
                 * label : null
                 * actionUrl : eyepetizer://pgc/detail/602/?title=%E4%B8%AD%E5%9B%BD%E5%BB%B6%E6%97%B6%E6%91%84%E5%BD%B1%E8%81%94%E7%9B%9F&userType=PGC&tabIndex=0
                 * labelList : null
                 * icon : http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg
                 * iconType : round
                 * description : 中国延时摄影联盟  /  #旅行  /  08' 19"
                 * time : 1443628800000
                 */

                var id: Int = 0
                var title: String? = null
                var font: Any? = null
                var cover: Any? = null
                var label: Any? = null
                var actionUrl: String? = null
                var labelList: Any? = null
                var icon: String? = null
                var iconType: String? = null
                var description: String? = null
                var time: Long = 0
            }

            class ContentBean {
                /**
                 * type : video
                 * data : {"dataType":"VideoBeanForClient","id":2508,"title":"韵律中国 2015","slogan":null,"description":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","provider":{"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"},"category":"旅行","author":{"id":602,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","name":"中国延时摄影联盟","description":"中国最大的延时摄影社群，策划制作《韵动中国》系列延时摄影影片。公众微信：TimelapseChina","link":"","latestReleaseTime":1489772311000,"videoNum":2,"adTrack":null,"follow":{"itemType":"author","itemId":602,"followed":false},"shield":{"itemType":"author","itemId":602,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true},"cover":{"feed":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","detail":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","blurred":"http://img.kaiyanapp.com/893c145d9e156953195ec2c3e6e300f2.jpeg?imageMogr2/quality/100","sharing":null,"homepage":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=default&source=qcloud","thumbPlayUrl":null,"duration":499,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=2508","forWeibo":"http://wandou.im/nfs3k"},"releaseTime":1443628800000,"library":"DAILY","playInfo":[{"height":360,"width":640,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud","size":58928742},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=ucloud","size":58928742}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud"},{"height":480,"width":854,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud","size":86818871},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=ucloud","size":86818871}],"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud"},{"height":720,"width":1280,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud","size":102044542},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=ucloud","size":102044542}],"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud"}],"consumption":{"collectionCount":15809,"shareCount":24388,"replyCount":103},"campaign":null,"waterMarks":null,"adTrack":null,"tags":[{"id":52,"name":"风光摄影","actionUrl":"eyepetizer://tag/52/?title=%E9%A3%8E%E5%85%89%E6%91%84%E5%BD%B1","adTrack":null},{"id":342,"name":"中国","actionUrl":"eyepetizer://tag/342/?title=%E4%B8%AD%E5%9B%BD","adTrack":null},{"id":78,"name":"延时","actionUrl":"eyepetizer://tag/78/?title=%E5%BB%B6%E6%97%B6","adTrack":null}],"type":"NORMAL","titlePgc":null,"descriptionPgc":null,"remark":null,"idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1443628800000,"promotion":null,"label":null,"labelList":[],"descriptionEditor":"静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China","collected":false,"played":false,"subtitles":[],"lastViewTime":null,"playlists":null}
                 * tag : null
                 */

                var type: String? = null
                var data: DataBeanXX? = null
                var tag: Any? = null

                class DataBeanXX {
                    /**
                     * dataType : VideoBeanForClient
                     * id : 2508
                     * title : 韵律中国 2015
                     * slogan : null
                     * description : 静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China
                     * provider : {"name":"YouTube","alias":"youtube","icon":"http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"}
                     * category : 旅行
                     * author : {"id":602,"icon":"http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg","name":"中国延时摄影联盟","description":"中国最大的延时摄影社群，策划制作《韵动中国》系列延时摄影影片。公众微信：TimelapseChina","link":"","latestReleaseTime":1489772311000,"videoNum":2,"adTrack":null,"follow":{"itemType":"author","itemId":602,"followed":false},"shield":{"itemType":"author","itemId":602,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true}
                     * cover : {"feed":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","detail":"http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100","blurred":"http://img.kaiyanapp.com/893c145d9e156953195ec2c3e6e300f2.jpeg?imageMogr2/quality/100","sharing":null,"homepage":null}
                     * playUrl : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=default&source=qcloud
                     * thumbPlayUrl : null
                     * duration : 499
                     * webUrl : {"raw":"http://www.eyepetizer.net/detail.html?vid=2508","forWeibo":"http://wandou.im/nfs3k"}
                     * releaseTime : 1443628800000
                     * library : DAILY
                     * playInfo : [{"height":360,"width":640,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud","size":58928742},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=ucloud","size":58928742}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud"},{"height":480,"width":854,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud","size":86818871},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=ucloud","size":86818871}],"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=normal&source=qcloud"},{"height":720,"width":1280,"urlList":[{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud","size":102044542},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=ucloud","size":102044542}],"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=high&source=qcloud"}]
                     * consumption : {"collectionCount":15809,"shareCount":24388,"replyCount":103}
                     * campaign : null
                     * waterMarks : null
                     * adTrack : null
                     * tags : [{"id":52,"name":"风光摄影","actionUrl":"eyepetizer://tag/52/?title=%E9%A3%8E%E5%85%89%E6%91%84%E5%BD%B1","adTrack":null},{"id":342,"name":"中国","actionUrl":"eyepetizer://tag/342/?title=%E4%B8%AD%E5%9B%BD","adTrack":null},{"id":78,"name":"延时","actionUrl":"eyepetizer://tag/78/?title=%E5%BB%B6%E6%97%B6","adTrack":null}]
                     * type : NORMAL
                     * titlePgc : null
                     * descriptionPgc : null
                     * remark : null
                     * idx : 0
                     * shareAdTrack : null
                     * favoriteAdTrack : null
                     * webAdTrack : null
                     * date : 1443628800000
                     * promotion : null
                     * label : null
                     * labelList : []
                     * descriptionEditor : 静待日月轮回，走过灯火阑珊。国内外 70 余位顶级延时摄影师历时一年，走遍神州大地，只为寻觅那最绚丽的色彩和最壮­美的风景。感谢@中国延时摄影联盟 的授权和每位摄影师的付出。 From Timelapse China
                     * collected : false
                     * played : false
                     * subtitles : []
                     * lastViewTime : null
                     * playlists : null
                     */

                    var dataType: String? = null
                    var id: Int = 0
                    var title: String? = null
                    var slogan: Any? = null
                    var description: String? = null
                    var provider: ProviderBeanX? = null
                    var category: String? = null
                    var author: AuthorBean? = null
                    var cover: CoverBeanX? = null
                    var playUrl: String? = null
                    var thumbPlayUrl: Any? = null
                    var duration: Int = 0
                    var webUrl: WebUrlBeanX? = null
                    var releaseTime: Long = 0
                    var library: String? = null
                    var consumption: ConsumptionBeanX? = null
                    var campaign: Any? = null
                    var waterMarks: Any? = null
                    var adTrack: Any? = null
                    var type: String? = null
                    var titlePgc: Any? = null
                    var descriptionPgc: Any? = null
                    var remark: Any? = null
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
                    var playInfo: MutableList<PlayInfoBeanX>? = null
                    var tags: MutableList<TagsBeanX>? = null
                    var labelList: MutableList<*>? = null
                    var subtitles: MutableList<*>? = null

                    class ProviderBeanX {
                        /**
                         * name : YouTube
                         * alias : youtube
                         * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                         */

                        var name: String? = null
                        var alias: String? = null
                        var icon: String? = null
                    }

                    class AuthorBean {
                        /**
                         * id : 602
                         * icon : http://img.kaiyanapp.com/20aa1ff5e7a2dccf30c8230fac0a0bfa.jpeg?imageMogr2/quality/60/format/jpg
                         * name : 中国延时摄影联盟
                         * description : 中国最大的延时摄影社群，策划制作《韵动中国》系列延时摄影影片。公众微信：TimelapseChina
                         * link :
                         * latestReleaseTime : 1489772311000
                         * videoNum : 2
                         * adTrack : null
                         * follow : {"itemType":"author","itemId":602,"followed":false}
                         * shield : {"itemType":"author","itemId":602,"shielded":false}
                         * approvedNotReadyVideoCount : 0
                         * ifPgc : true
                         */

                        var id: Int = 0
                        var icon: String? = null
                        var name: String? = null
                        var description: String? = null
                        var link: String? = null
                        var latestReleaseTime: Long = 0
                        var videoNum: Int = 0
                        var adTrack: Any? = null
                        var follow: FollowBean? = null
                        var shield: ShieldBean? = null
                        var approvedNotReadyVideoCount: Int = 0
                        var isIfPgc: Boolean = false

                        class FollowBean {
                            /**
                             * itemType : author
                             * itemId : 602
                             * followed : false
                             */

                            var itemType: String? = null
                            var itemId: Int = 0
                            var isFollowed: Boolean = false
                        }

                        class ShieldBean {
                            /**
                             * itemType : author
                             * itemId : 602
                             * shielded : false
                             */

                            var itemType: String? = null
                            var itemId: Int = 0
                            var isShielded: Boolean = false
                        }
                    }

                    class CoverBeanX {
                        /**
                         * feed : http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100
                         * detail : http://img.kaiyanapp.com/8469265bc4b026850d31037e2778b9a5.jpeg?imageMogr2/quality/100
                         * blurred : http://img.kaiyanapp.com/893c145d9e156953195ec2c3e6e300f2.jpeg?imageMogr2/quality/100
                         * sharing : null
                         * homepage : null
                         */

                        var feed: String? = null
                        var detail: String? = null
                        var blurred: String? = null
                        var sharing: Any? = null
                        var homepage: Any? = null
                    }

                    class WebUrlBeanX {
                        /**
                         * raw : http://www.eyepetizer.net/detail.html?vid=2508
                         * forWeibo : http://wandou.im/nfs3k
                         */

                        var raw: String? = null
                        var forWeibo: String? = null
                    }

                    class ConsumptionBeanX {
                        /**
                         * collectionCount : 15809
                         * shareCount : 24388
                         * replyCount : 103
                         */

                        var collectionCount: Int = 0
                        var shareCount: Int = 0
                        var replyCount: Int = 0
                    }

                    class PlayInfoBeanX {
                        /**
                         * height : 360
                         * width : 640
                         * urlList : [{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud","size":58928742},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=ucloud","size":58928742}]
                         * name : 流畅
                         * type : low
                         * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud
                         */

                        var height: Int = 0
                        var width: Int = 0
                        var name: String? = null
                        var type: String? = null
                        var url: String? = null
                        var urlList: MutableList<UrlListBeanX>? = null

                        class UrlListBeanX {
                            /**
                             * name : qcloud
                             * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=2508&editionType=low&source=qcloud
                             * size : 58928742
                             */

                            var name: String? = null
                            var url: String? = null
                            var size: Int = 0
                        }
                    }

                    class TagsBeanX {
                        /**
                         * id : 52
                         * name : 风光摄影
                         * actionUrl : eyepetizer://tag/52/?title=%E9%A3%8E%E5%85%89%E6%91%84%E5%BD%B1
                         * adTrack : null
                         */

                        var id: Int = 0
                        var name: String? = null
                        var actionUrl: String? = null
                        var adTrack: Any? = null
                    }
                }
            }
        }
    }
}
