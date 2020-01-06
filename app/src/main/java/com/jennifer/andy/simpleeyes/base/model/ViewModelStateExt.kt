package com.jennifer.andy.simpleeyes.base.model

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2019-12-26 19:48
 * Description:
 */

fun <T : Any>handleInit(result: Result<T>?, behaviorSubject: BehaviorSubject<ViewState<T>>, doOnSuccess: (T?) -> Unit = {}) {
    when (result) {
        is Result.Idle -> behaviorSubject.copyMap {
            it.copy(isLoaded = false, action = Action.INIT, throwable = null, data = null)
        }
        is Result.Error -> behaviorSubject.copyMap {
            it.copy(isLoaded = true, action = Action.INIT_FAIL, throwable = result.throwable)
        }
        is Result.Success -> {
            behaviorSubject.copyMap { it.copy(action = Action.INIT_SUCCESS, data = result.data) }
            doOnSuccess(result.data)
        }
    }
}


fun <T : Any> handleRefresh(result: Result<T>?, behaviorSubject: BehaviorSubject<ViewState<T>>, doOnSuccess: (T?) -> Unit = {}) {
    when (result) {
        is Result.Idle -> behaviorSubject.copyMap {
            it.copy(isLoaded = false, action = Action.REFRESH, throwable = null, data = null)
        }
        is Result.Error -> behaviorSubject.copyMap {
            it.copy(isLoaded = true, action = Action.REFRESH_FAIL, throwable = result.throwable)
        }
        is Result.Success -> {
            behaviorSubject.copyMap { it.copy(action = Action.REFRESH_SUCCESS, data = result.data) }
            doOnSuccess(result.data)
        }
    }
}


fun <T : Any> handleLoadMore(result: Result<T>?, behaviorSubject: BehaviorSubject<ViewState<T>>, doOnSuccess: (T?) -> Unit = {}) {
    when (result) {
        is Result.Idle -> behaviorSubject.copyMap {
            it.copy(isLoaded = false, action = Action.LOAD_MORE, throwable = null, data = null)
        }
        is Result.Error -> behaviorSubject.copyMap {
            it.copy(isLoaded = true, action = Action.LOAD_MORE_FAIL, throwable = result.throwable)
        }
        is Result.Success -> {
            behaviorSubject.copyMap {
                if (result.data == null)
                    it.copy(action = Action.HAVE_NO_MORE)
                else
                    it.copy(action = Action.LOAD_MORE_SUCCESS, data = result.data)
            }
            doOnSuccess(result.data)
        }
    }
}