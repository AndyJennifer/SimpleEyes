package com.jennifer.andy.simpleeyes.ui.video

import com.jennifer.andy.base.rx.RxThreadHelper
import com.jennifer.andy.base.viewmodel.AutoDisposeViewModel
import com.jennifer.andy.simpleeyes.base.model.handleInit
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.result.Result
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.video.usecase.VideoUseCase
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2020-02-10 14:21
 * Description:
 */

class VideoViewModel(private val videoUseCase: VideoUseCase) : AutoDisposeViewModel() {

    private val mRelateVideoSubject = BehaviorSubject.createDefault(ViewState.init<AndyInfo>())

    private val mVideoInfoSubject = BehaviorSubject.createDefault(ViewState.init<ContentBean>())


    fun observeRelativeVideoInfo(): Observable<ViewState<AndyInfo>> = mRelateVideoSubject.hide().distinctUntilChanged()

    fun observeVideoInfo(): Observable<ViewState<ContentBean>> = mVideoInfoSubject.hide().distinctUntilChanged()

    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) =
            videoUseCase.getRelatedVideoInfo(id)
                    .startWith(Result.idle())
                    .compose(RxThreadHelper.switchFlowableThread())
                    .autoDispose(this)
                    .subscribe { result ->
                        handleInit(result, mRelateVideoSubject)
                    }


    /**
     * 根据视频id获取视频信息
     */
    fun getVideoInfoById(id: String) =
            videoUseCase.getVideoInfoById(id)
                    .startWith(Result.idle())
                    .compose(RxThreadHelper.switchFlowableThread())
                    .autoDispose(this)
                    .subscribe { result ->
                        handleInit(result, mVideoInfoSubject)
                    }
}