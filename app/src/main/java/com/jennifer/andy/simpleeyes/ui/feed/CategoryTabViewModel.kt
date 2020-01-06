package com.jennifer.andy.simpleeyes.ui.feed

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.entity.Category
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.feed.usecase.FeedUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-01-06 22:09
 * Description:
 */
class CategoryTabViewModel(private val feedUseCase: FeedUseCase) : AutoDisposeViewModel() {


    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<Category>())

    fun observeViewState(): Observable<ViewState<Category>> = mViewStateSubject.hide().distinctUntilChanged()

    fun getCategoryTabIno(id: String) {
        feedUseCase.getCategoryTabIno(id)
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { handleInit(it, mViewStateSubject) }
    }

}