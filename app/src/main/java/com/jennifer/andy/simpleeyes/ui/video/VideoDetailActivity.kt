package com.jennifer.andy.simpleeyes.ui.video

import android.os.Bundle
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.player.IjkVideoView
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoDetailPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>() {

    private lateinit var mVideoInfo: ContentBean
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)

    private var mBackPressed = false

    override fun getBundleExtras(extras: Bundle) {
        mVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBlurredImage.setImageURI(mVideoInfo.data.cover.blurred)
        val videoPath = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=${mVideoInfo.data.id}&editionType=high&source=aliyun&d0f6190461864a3a978bdbcb3fe9b48709f1f390&token=55675f3722ad26dc"
        mVideoView.setVideoPath(videoPath)
        mVideoView.start()
        // todo 使用共享动画，完成界面的移动
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        mBackPressed = true
    }

    override fun onStop() {
        super.onStop()
        if (mBackPressed) {
            mVideoView.stopPlayback()
            mVideoView.release(true)
        }
        IjkMediaPlayer.native_profileEnd()
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition(): TransitionMode = TransitionMode.BOTTOM

    override fun getContentViewLayoutId() = R.layout.activity_video_detail

}