package com.jennifer.andy.simpleeyes.net.page


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 11:03
 * Description:pageIndex自增策略
 */

class PagePlusStartegy : PageStartegy() {


    override fun handlePageIndex(currPageIndex: Int, pageSize: Int): Int {
        return currPageIndex + 1
    }
}