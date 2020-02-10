package com.jennifer.andy.simpleeyes.ui.common

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.base.model.handleLoadMore
import com.jennifer.andy.simpleeyes.base.usecase.LoadMoreUseCase
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-02-10 15:23
 * Description:
 */

class CommonRecyclerViewModel(private val loadMoreUseCase: LoadMoreUseCase) : AutoDisposeViewModel() {


    private var mNextPageUrl: String? = null
    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    fun observeDataInfo(): Observable<ViewState<AndyInfo>> = mViewStateSubject.hide().distinctUntilChanged()


    fun getDataFromUrl(url: String) {
        loadMoreUseCase.getDataFromUrl(url)
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mViewStateSubject) {
                        mNextPageUrl = it?.nextPageUrl
                    }
                }
    }

    /**
     * 加载更多首页信息
     */
    fun loadMoreDataFromUrl() {
        if (mNextPageUrl != null) {
            loadMoreUseCase.getDataFromUrl(mNextPageUrl!!)
                    .startWith(Result.idle())
                    .compose(RxThreadHelper.switchFlowableThread())
                    .autoDispose(this)
                    .subscribe { result ->
                        handleLoadMore(result, mViewStateSubject) {
                            mNextPageUrl = it?.nextPageUrl
                        }
                    }
        } else {
            mViewStateSubject.copyMap { it.copy(action = Action.HAVE_NO_MORE) }
        }
    }

}