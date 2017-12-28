package com.jennifer.andy.simpleeyes.ui.video

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoDetailPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>() {

    private lateinit var mVideoInfo: ContentBean

    override fun initView(savedInstanceState: Bundle?) {
        //加载背景图
        //加载视频
        //加载相关

    }

    override fun getBundleExtras(extras: Bundle) {
        mVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean
    }

    override fun getContentViewLayoutId() = R.layout.activity_video_detail
}