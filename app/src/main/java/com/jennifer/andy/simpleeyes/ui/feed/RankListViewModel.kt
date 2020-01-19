package com.jennifer.andy.simpleeyes.ui.feed

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.feed.usecase.FeedUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 13:23
 * Description:
 */

class RankListViewModel(private val feedUseCase: FeedUseCase) : AutoDisposeViewModel() {


    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<Tab>())

    fun observeViewState(): Observable<ViewState<Tab>> = mViewStateSubject.hide().distinctUntilChanged()

    fun getRankListTab() {
        feedUseCase.getRankListTab()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { handleInit(it, mViewStateSubject) }
    }

}