package com.jennifer.andy.simpleeyes.ui.base

import com.jennifer.andy.simpleeyes.ui.base.action.Action


/**
 * Author:  andy.xwt
 * Date:    2019-12-25 10:59
 * Description:
 * 创建主界面需要展示的数据，这里重写其equals与hashCode方法是为了方便以后使用map
 * @see isLoaded  是否已经加载
 * @see throwable 抛出的错误
 * @see data  加载的数据
 */

data class ViewState<T : Any>(
        val isLoaded: Boolean,
        val action: Action,
        val throwable: Throwable?,
        val data: T?) {


    companion object {
        fun <T : Any> init() = ViewState<T>(
                isLoaded = false,
                action = Action.INIT,
                throwable = null,
                data = null)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ViewState<*>

        if (isLoaded != other.isLoaded) return false
        if (action != other.action) return false
        if (throwable != other.throwable) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoaded.hashCode()
        result = 31 * result + action.hashCode()
        result = 31 * result + throwable.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}