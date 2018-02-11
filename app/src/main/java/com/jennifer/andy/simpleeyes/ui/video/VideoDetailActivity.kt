package com.jennifer.andy.simpleeyes.ui.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.entity.ItemListBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.player.IRenderView
import com.jennifer.andy.simpleeyes.player.IjkMediaController
import com.jennifer.andy.simpleeyes.player.IjkVideoView
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.adapter.VideoDetailAdapter
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoDetailPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>(), VideoDetailView {

    private lateinit var mVideoInfo: ContentBean

    private val mShareImage by bindView<SimpleDraweeView>(R.id.iv_place_image)
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)
    private val mProgress by bindView<ProgressBar>(R.id.progress)
    private val mHorizontalProgress by bindView<ProgressBar>(R.id.sb_progress)
    private val mRecycler by bindView<RecyclerView>(R.id.rv_recycler)

    private var mBackPressed = false

    private lateinit var mVideoDetailAdapter: VideoDetailAdapter
    private lateinit var ijkMediaController: IjkMediaController

    override fun getBundleExtras(extras: Bundle) {
        mVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean

    }

    override fun initView(savedInstanceState: Bundle?) {
        initPlaceHolder()
        initMediaController()
        playVideo()
    }


    private fun initPlaceHolder() {
        mShareImage.setImageURI(mVideoInfo.data.cover.detail)
        mBlurredImage.setImageURI(mVideoInfo.data.cover.blurred)
    }

    /**
     * 播放视频
     */
    private fun playVideo() {
        val videoPath = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=${mVideoInfo.data.id}&editionType=high&source=aliyun&d0f6190461864a3a978bdbcb3fe9b48709f1f390&token=55675f3722ad26dc"
        with(mVideoView) {
            setVideoPath(videoPath)
            setMediaController(ijkMediaController)
            start()
            //设置准备完成监听
            setOnPreparedListener {
                //隐藏进度条
                handler.postDelayed({
                    mShareImage.visibility = View.GONE
                    mProgress.visibility = View.GONE
                }, 600)
                //获取相关视屏信息
                mPresenter.getRelatedVideoInfo(mVideoInfo.data.id)
            }
            toggleAspectRatio(IRenderView.AR_MATCH_PARENT)

            //设置完成监听
            setOnCompletionListener {
                // todo 完成后更改布局
            }
        }

    }

    /**
     * 添加标题
     */
    private fun addTitle() {
        //todo 添加videoDetailView
    }

    /**
     * 设置相关视频信息
     */
    private fun setRelationVideoInfo() {

    }

    override fun getRelatedVideoInfoSuccess(itemList: MutableList<ItemListBean>) {
        mVideoDetailAdapter = VideoDetailAdapter(itemList)
        mVideoDetailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mVideoDetailAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.item_the_end, null))
        mRecycler.layoutManager = LinearLayoutManager(mContext)
        mRecycler.adapter = mVideoDetailAdapter
    }

    override fun getRelatedVideoFail() {
        //todo 加载空界面，
    }

    private fun initMediaController() {
        mHorizontalProgress.max = 1000
        ijkMediaController = IjkMediaController(mContext)
        ijkMediaController.setOnProgressChangeListener { progress, secondaryProgress ->
            mHorizontalProgress.progress = progress
            mHorizontalProgress.secondaryProgress = secondaryProgress
        }
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        mBackPressed = true
    }


    override fun onPause() {
        super.onPause()
        if (mVideoView.isPlaying) {
            mVideoView.pause()
        }

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