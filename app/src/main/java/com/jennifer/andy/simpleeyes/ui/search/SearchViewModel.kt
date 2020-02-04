package com.jennifer.andy.simpleeyes.ui.search

import com.jennifer.andy.base.ext.reactivex.copyMap
import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.base.model.handleLoadMore
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.search.usecase.SearchUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:17
 * Description:
 */

class SearchViewModel(private val searchUseCase: SearchUseCase) : AutoDisposeViewModel() {


    private var mNextPageUrl: String? = null

    private val mAndyInfoStateSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    private val mStringStateSubject = BehaviorSubject.createDefault(ViewState.init<MutableList<String>>())

    fun observeAndyInfoState(): Observable<ViewState<AndyInfo>> = mAndyInfoStateSubject.hide().distinctUntilChanged()

    fun observeStringState(): Observable<ViewState<MutableList<String>>> = mStringStateSubject.hide().distinctUntilChanged()

    /**
     * 获取热门关键词
     */
    fun getHotWord() {
        searchUseCase.getHotWord()
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mStringStateSubject)
                }
    }


    /**
     * 根据关键字搜索视频
     */
    fun searchVideoByWord(word: String) {
        searchUseCase.searchVideoByWord(word)
                .startWith(Result.idle())
                .compose(RxThreadHelper.switchFlowableThread())
                .autoDispose(this)
                .subscribe { result ->
                    handleInit(result, mAndyInfoStateSubject) {
                        mNextPageUrl = it?.nextPageUrl
                    }
                }
    }

    /**
     * 加载更多信息
     */
    fun loadMoreAndyInfo() {
        if (mNextPageUrl != null) {
            searchUseCase.loadMoreAndyInfo(mNextPageUrl!!)
                    .startWith(Result.idle())
                    .compose(RxThreadHelper.switchFlowableThread())
                    .autoDispose(this)
                    .subscribe { result ->
                        handleLoadMore(result, mAndyInfoStateSubject) {
                            mNextPageUrl = it?.nextPageUrl
                        }
                    }
        } else {
            mAndyInfoStateSubject.copyMap { it.copy(action = Action.HAVE_NO_MORE) }
        }
    }
}