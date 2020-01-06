package com.jennifer.andy.simpleeyes.ui.video.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.video.model.VideoDetailModel
import com.jennifer.andy.simpleeyes.ui.video.view.VideoInfoByIdView
import com.uber.autodispose.autoDispose


/**
 * Author:  andy.xwt
 * Date:    2018/7/23 14:32
 * Description:
 */

class VideoInfoByIdPresenter : BasePresenter<VideoInfoByIdView>() {

    private val mVideoModel = VideoDetailModel()

    fun getVideoInfoById(id: String) {
        mVideoModel.getVideoInfoById(id).autoDispose(mScopeProvider).subscribe({
            mView?.getVideoInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                getVideoInfoById(id)
            })
        })
    }
}