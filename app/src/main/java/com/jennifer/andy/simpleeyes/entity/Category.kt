package com.jennifer.andy.simpleeyes.entity


/**
 * Author:  andy.xwt
 * Date:    2018/8/6 10:21
 * Description:
 */

data class Category(var categoryInfo: CategoryInfo,
                    var tabInfo: TabInfo)

data class CategoryInfo(var dataType: String,
                        var id: String,
                        var name: String,
                        var description: String,
                        var headerImage: String,
                        var actionUrl: String,
                        var followInfo: FollowInfo)

data class FollowInfo(var itemType: String,
                      var itemId: String,
                      var followed: Boolean)

