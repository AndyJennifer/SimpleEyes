package com.jennifer.andy.simpleeyes.ui.author

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.entity.Tab
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.author.usecase.AuthorUseCase
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 14:35
 * Description:
 */

class AuthorTagViewModel(private val authorUseCase: AuthorUseCase) : AutoDisposeViewModel() {

    private val mViewStateSubject = BehaviorSubject.createDefault(ViewState.init<Tab>())

    fun observeViewState(): Observable<ViewState<Tab>> = mViewStateSubject.hide().distinctUntilChanged()

    fun getAuthorTagDetail(id: String) {
        authorUseCase.getAuthorTagDetail(id)
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { handleInit(it, mViewStateSubject)
                }
    }

}