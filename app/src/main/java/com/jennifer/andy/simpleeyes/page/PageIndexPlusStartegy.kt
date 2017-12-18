package com.jennifer.andy.simpleeyes.page


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 11:09
 * Description:startIndex 与endIndex加载策略
 */

class PageIndexPlusStartegy : PageStartegy() {

    override fun handlePageIndex(currPageIndex: Int, pageSize: Int): Int {
        return currPageIndex + pageSize
    }
}