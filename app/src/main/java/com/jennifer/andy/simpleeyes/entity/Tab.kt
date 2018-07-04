package com.jennifer.andy.simpleeyes.entity

import java.io.Serializable


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:03
 * Description: 发现tab栏信息
 */


data class Tab(var tabInfo: TabInfo)

data class TabInfo(var tabList: MutableList<TabDetailInfo>,
        var defaultIdx: Int) : Serializable


data class TabDetailInfo(var id: Int,
                         var name: String,
                         var apiUrl: String)