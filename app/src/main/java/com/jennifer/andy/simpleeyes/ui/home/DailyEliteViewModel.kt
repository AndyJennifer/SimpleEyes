package com.jennifer.andy.simpleeyes.ui.home

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.base.model.handleLoadMore
import com.jennifer.andy.simpleeyes.base.model.handleRefresh
import com.jennifer.andy.simpleeyes.net.entity.JenniferInfo
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.home.usecase.HomeUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2019-12-26 14:45
 * Description:
 */

class DailyEliteViewModel(private val homeUserCase: HomeUseCase) : AutoDisposeViewModel() {

    private var mNextPageUrl: String? = null
    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<JenniferInfo>())


    fun observeViewState(): Observable<ViewState<JenniferInfo>> = mViewStateSubject.hide().distinctUntilChanged()

    /**
     * 获取每日编辑精选
     */
    fun getDailyElite() {
        homeUserCase.getDailyElite()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mViewStateSubject) { mNextPageUrl = it?.nextPageUrl }
                }
    }

    /**
     * 刷新
     */
    fun refresh() {
        homeUserCase.getDailyElite()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleRefresh(result, mViewStateSubject) { mNextPageUrl = it?.nextPageUrl }
                }
    }

    /**
     * 加載更多
     */
    fun loadMoreJenniferInfo() {
        if (mNextPageUrl != null) {
            homeUserCase.loadMoreJenniferInfo(mNextPageUrl!!)
                    .startWith(Result.idle())
                    .compose(RxThreadHelper.switchFlowableThread())
                    .autoDispose(this).subscribe { result ->
                        handleLoadMore(result, mViewStateSubject) { mNextPageUrl = it?.nextPageUrl }
                    }
        } else {
            mViewStateSubject.copyMap { it.copy(action = Action.HAVE_NO_MORE) }
        }
    }
}