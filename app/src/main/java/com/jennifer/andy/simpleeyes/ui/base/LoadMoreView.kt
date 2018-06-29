package com.jennifer.andy.simpleeyes.ui.base


/**
 * Author:  andy.xwt
 * Date:    2018/6/30 01:50
 * Description:
 */
interface LoadMoreView<T> : BaseView {

    /**
     * 加载更多信息成功
     */
    fun loadMoreSuccess(data: T)

    /**
     * 没有更多
     */
    fun showNoMore()
}
