package com.jennifer.andy.simplemusic.manager

import android.app.Activity
import java.util.*

/**
 * Author:  andy.xwt
 * Date:    2017/8/14 00:16
 * Description:
 */


class BaseApp private constructor() {

    /**
     * 返回当前activity的个数
     */
    fun size(): Int {
        return sActivityList.size
    }

    /**
     * 获得前一个activity
     */
    val forwardActivity: Activity?
        @Synchronized get() = if (size() > 0) sActivityList[size() - 1] else null

    /**
     * 添加相应的activity到相应的集合中
     */
    @Synchronized fun addActivity(activity: Activity) {
        sActivityList.add(activity)
    }

    /**
     * 从集合中移除相应的activity
     */
    @Synchronized fun removeActivity(activity: Activity) {
        if (sActivityList.contains(activity)) {
            sActivityList.remove(activity)
        }
    }

    /**
     * 清除所有的activity
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
     * 只要最上面的activity
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

    companion object {

        private var mInstance: BaseAppManager? = null

        private val sActivityList = ArrayList<Activity>()//当前应用的activity集合

        /**
         * 懒汉式只会在两个线程进入创建过程的时候才管同步这个问题

         * @return
         */
        val instance: BaseAppManager?
            get() {
                if (null == mInstance) {
                    synchronized(BaseAppManager::class.java) {
                        if (null == mInstance) {
                            mInstance = BaseAppManager()
                        }
                    }
                }
                return mInstance
            }
    }
}
