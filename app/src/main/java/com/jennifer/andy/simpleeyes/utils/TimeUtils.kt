package com.jennifer.andy.simpleeyes.utils


/**
 * Author:  andy.xwt
 * Date:    2017/11/23 18:20
 * Description:
 */

object TimeUtils {

    /**
     * 将秒数转换为 00 00' 00''格式
     */
    fun getElapseTimeForShow(seconds: Int): String {
        val sb = StringBuilder()
        val hour = seconds / (60 * 60)
        if (hour != 0) {
            sb.append(hour).append("")
        }
        val minute = (seconds - 60 * 60 * hour) / 60
        if (minute != 0 && minute > 9) {
            sb.append(minute).append("' ")
        } else {
            sb.append("0").append(minute).append("' ")
        }
        val second = seconds - 60 * 60 * hour - 60 * minute
        if (second != 0 && second > 9) {
            sb.append(second).append("\"")
        } else {
            sb.append("0").append(second).append("\"")
        }
        return sb.toString()
    }

}