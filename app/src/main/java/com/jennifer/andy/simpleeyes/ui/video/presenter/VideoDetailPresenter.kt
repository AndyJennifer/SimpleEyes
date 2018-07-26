package com.jennifer.andy.simpleeyes.ui.video.presenter

import com.jennifer.andy.simpleeyes.ui.base.presenter.BasePresenter
import com.jennifer.andy.simpleeyes.ui.video.model.VideoDetailModel
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:55
 * Description:
 */
class VideoDetailPresenter : BasePresenter<VideoDetailView>() {

    private var mVideoModel: VideoDetailModel = VideoDetailModel()

    /**
     * 获取相关视频信息
     */
    fun getRelatedVideoInfo(id: String) {
        mRxManager.add(mVideoModel.getRelatedVideoInfo(id).subscribe({
            mView?.getRelatedVideoInfoSuccess(it.itemList)
        }, {
            mView?.getRelatedVideoFail()
        }))
    }


}
