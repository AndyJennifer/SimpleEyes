package com.jennifer.andy.simplemusic.manager

import android.app.Activity


/**
 * Author:  andy.xwt
 * Date:    2017/8/14 00:15
 * Description:Activity管理类
 */

class BaseAppManager private constructor() {


    companion object {
        /**
         * 创建单例模式
         */
        //todo  在研究一下单例
        private val mInstance: BaseAppManager get() = BaseAppManager()
        private val sActivityList = ArrayList<Activity>()
    }

    /**
     * 当前activity的个数
     */
    val mActivitySize: Int get() = sActivityList.size

    val mForwardActivity: Activity? @Synchronized get() = if (mActivitySize > 0) sActivityList[mActivitySize - 1] else null

    /**
     * 添加相应activity
     */
    @Synchronized fun removeActivity(activity: Activity) {
        if (activity in sActivityList) sActivityList.remove(activity)
    }

    /**
     * 添加相应的activity
     */
    @Synchronized fun addActivity(activity: Activity) {
        sActivityList.add(activity)
    }

    /**
     * 清除栈内activity
     */
    @Synchronized fun clear() {
        var i = sActivityList.size - 1
        while (i > -1) {
            val activity = sActivityList[i]
            removeActivity(activity)
            activity.finish()
            i = sActivityList.size
            i--
        }
    }

    /**
     * 清除栈顶activity
     */
    @Synchronized fun clearToTop() {
        var i = sActivityList.size - 2
        while (i > -1) {
            val activity = sActivityList[i]
            removeActivity(activity)
            activity.finish()
            i = sActivityList.size - 1
            i--
        }
    }
}