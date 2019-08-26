package com.jennifer.andy.simpleeyes.ui.base.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.LoadMoreView
import com.jennifer.andy.simpleeyes.ui.base.model.BaseModel


/**
 * Author:  andy.xwt
 * Date:    2019-07-10 22:24
 * Description: 加载更多Presenter。
 * 第一个泛型参数T:为加载更多对应的数据类型
 * 第二个泛型参数M：为对应model
 * 第三个泛型参数V：为实现了加载更多的View
 */

open class LoadMorePresenter<T, M : BaseModel, V : LoadMoreView<T>> : BasePresenter<V>() {

    protected var mNextPageUrl: String? = null
    protected open lateinit var mBaseModel: M

    fun loadMoreInfo() {
        if (mNextPageUrl != null) {
            mRxManager.add(mBaseModel.loadMoreAndyInfo(mNextPageUrl)!!.subscribe({
                mView?.showContent()
                if (mNextPageUrl == null) {
                    mView?.showNoMore()
                } else {
                    mNextPageUrl = it.nextPageUrl
                    mView?.loadMoreSuccess(it as T)
                }
            }, {
                mView?.showNetError(View.OnClickListener {
                    loadMoreInfo()
                })
            }))
        } else {
            mView?.showNoMore()
        }
    }
}