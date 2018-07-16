package com.jennifer.andy.simpleeyes.net.page


/**
 * Author:  andy.xwt
 * Date:    2017/12/15 18:18
 * Description:分页策略抽象类
 */

abstract class PageStartegy {

    private val DEFAULT_START_PAGE_INDEX = 0   // 默认起始页下标
    private val DEFAULT_PAGE_SIZE = 10    // 默认分页大小

    protected var mCurrPageIndex = DEFAULT_START_PAGE_INDEX // 当前页下标
    private var mLastPageIndex = DEFAULT_START_PAGE_INDEX // 记录上一次的页下标
    protected var mPageSize = DEFAULT_PAGE_SIZE//分页大小

    // 分页大小
    private var isLoading: Boolean = false // 是否正在加载
    private val lock = Any() // 锁


    /**
     * 根据分页策略,处理分页参数
     */
    abstract fun handlePageIndex(currPageIndex: Int, pageSize: Int): Int


    /**
     * 分页加载数据
     * [可能会抛出异常，请确认数据加载结束后，你已经调用了finishLoad(boolean success)方法]
     *
     * @param isFirstPage true: 第一页  false: 下一页
     */
    fun loadPage(isFirstPage: Boolean) {
        synchronized(lock) {
            if (isLoading) { // 如果正在加载数据，则抛出异常
                throw RuntimeException()
            } else {
                isLoading = true
            }
        }
        if (!isFirstPage) {
            mCurrPageIndex = handlePageIndex(mCurrPageIndex, mPageSize)
        }
    }

    /**
     * 加载结束
     *
     * @param success true：加载成功  false：失败(无数据)
     */
    fun finishLoad(success: Boolean) {
        synchronized(lock) {
            isLoading = false
        }
        if (success) {
            mLastPageIndex = mCurrPageIndex
        } else {
            mCurrPageIndex = mLastPageIndex
        }
    }

}