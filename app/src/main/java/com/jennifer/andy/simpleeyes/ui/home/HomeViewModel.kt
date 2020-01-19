package com.jennifer.andy.simpleeyes.ui.home

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
import com.jennifer.andy.simpleeyes.ui.home.usecase.HomeUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2019-12-08 19:41
 * Description:
 */

class HomeViewModel(private val homeUserCase: HomeUseCase) : AutoDisposeViewModel() {


    private var mNextPageUrl: String? = null
    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    fun observeViewState(): Observable<ViewState<AndyInfo>> = mViewStateSubject.hide().distinctUntilChanged()


    /**
     * 加载首页信息
     */
    fun loadCategoryData() {
        homeUserCase.loadCategoryData()
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
     * 刷新首页信息延迟1秒执行
     */
    fun refreshCategoryData() {
        homeUserCase.loadCategoryData()
                .delay(1000, TimeUnit.MILLISECONDS)
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleRefresh(result, mViewStateSubject) {
                        mNextPageUrl = it?.nextPageUrl
                    }
                }
    }


    /**
     * 加载更多首页信息
     */
    fun loadMoreAndyInfo() {
        if (mNextPageUrl != null) {
            homeUserCase.loadMoreAndyInfo(mNextPageUrl!!)
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