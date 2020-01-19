package com.jennifer.andy.simpleeyes.ui.follow

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.base.model.handleLoadMore
import com.jennifer.andy.simpleeyes.base.model.handleRefresh
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.follow.usecase.FollowUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-01-04 23:00
 * Description:
 */

class FollowViewModel(private val followUseCase: FollowUseCase) : AutoDisposeViewModel() {


    private var mNextPageUrl: String? = null
    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    fun observeViewState(): Observable<ViewState<AndyInfo>> = mViewStateSubject.hide().distinctUntilChanged()

    /**
     * 获取关注
     */
    fun getFollowInfo() {
        followUseCase.getFollowInfo()
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
     * 获取更多数据
     */
    fun loadMoreAndyInfo() {
        if (mNextPageUrl != null) {
            followUseCase.loadMoreAndyInfo(mNextPageUrl!!)
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

    /**
     * 刷新界面
     */
    fun refreshFollowInfo() {
        followUseCase.getFollowInfo()
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleRefresh(result, mViewStateSubject) {
                        mNextPageUrl = it?.nextPageUrl
                    }
                }
    }

    /**
     * 获取全部作者
     */
    fun getAllAuthor() {
        followUseCase.getAllAuthor()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mViewStateSubject) {
                        mNextPageUrl = it?.nextPageUrl
                    }
                }
    }

}
