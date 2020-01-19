package com.jennifer.andy.simpleeyes.ui.feed

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.base.model.handleLoadMore
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.feed.usecase.FeedUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 13:32
 * Description:
 */

class TopicViewModel(private val feedUseCase: FeedUseCase) : AutoDisposeViewModel() {

    private var mNextPageUrl: String? = null
    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    fun observeViewState(): Observable<ViewState<AndyInfo>> = mViewStateSubject.hide().distinctUntilChanged()

    fun getTopicInfo() {
        feedUseCase.getTopicInfo()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mViewStateSubject) { mNextPageUrl = it?.nextPageUrl }
                }
    }

    fun loadMoreInfo() {
        if (mNextPageUrl != null) {
            feedUseCase.loadMoreDataFromUrl(mNextPageUrl!!)
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