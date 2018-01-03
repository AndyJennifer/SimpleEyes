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


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>() {

    private lateinit var mVideoInfo: ContentBean
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)

    override fun initView(savedInstanceState: Bundle?) {
        //加载背景图
        //加载视频
        //加载相关
        //todo 完成视频的加载逻辑，完成视频的大小控制
        mBlurredImage.setImageURI(mVideoInfo.data.cover.blurred)
        mVideoView.setVideoPath("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=10556&editionType=high&source=aliyun&udid=d0f6190461864a3a978bdbcb3fe9b48709f1f390&token=55675f3722ad26dc")
        mVideoView.start()
    }

    override fun getBundleExtras(extras: Bundle) {
        mVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean
    }

    override fun getContentViewLayoutId() = R.layout.activity_video_detail
}