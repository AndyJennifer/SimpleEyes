package com.jennifer.andy.simpleeyes.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 15:03
 * Description:自定义加载更多
 */

class CustomLoadMoreView : LoadMoreView() {

    override fun getLayoutId() = R.layout.layout_load_more_view

    override fun getLoadingViewId() = R.id.ll_load_more_loading_view

    override fun getLoadEndViewId() = R.id.rl_load_end_view

    override fun getLoadFailViewId() = R.id.fl_load_more_load_fail_view
}