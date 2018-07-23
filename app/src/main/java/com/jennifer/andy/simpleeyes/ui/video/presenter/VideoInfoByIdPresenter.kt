package com.jennifer.andy.simpleeyes.ui.video.presenter

import android.view.View
import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailModel
import com.jennifer.andy.simpleeyes.ui.video.view.VideoInfoByIdView


/**
 * Author:  andy.xwt
 * Date:    2018/7/23 14:32
 * Description:
 */

class VideoInfoByIdPresenter : BasePresenter<VideoInfoByIdView>() {

    private val mVideoModel = VideoDetailModel()

    fun getVideoInfoById(id: String) {
        mRxManager.add(mVideoModel.getVideoInfoById(id).subscribe({
            mView?.getVideoInfoSuccess(it)
        }, {
            mView?.showNetError(View.OnClickListener {
                getVideoInfoById(id)
            })
        }))
    }

}