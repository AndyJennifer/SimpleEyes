package com.jennifer.andy.simpleeyes.ui.video

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jennifer.andy.base.rx.RxBus
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityVideoDetailBinding
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.net.entity.Content
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import com.jennifer.andy.simpleeyes.player.IjkMediaController
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.player.render.IRenderView
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.video.adapter.VideoDetailAdapter
import com.jennifer.andy.simpleeyes.widget.VideoDetailAuthorView
import com.jennifer.andy.simpleeyes.widget.pull.head.VideoDetailHeadView
import com.uber.autodispose.android.lifecycle.autoDispose
import io.reactivex.functions.Consumer
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */
class VideoDetailActivity : BaseDataBindActivity<ActivityVideoDetailBinding>() {

    private var mBackPressed = false //回退键是否被点击
    private var mCurrentIndex = 0  //当前视频播放的角标
    private var mChangeProgress: Int = 0 //视频播放进度

    private lateinit var mVideoListInfo: MutableList<Content>//其他视频信息
    private lateinit var mCurrentVideoInfo: ContentBean //当前播放的视频信息
    private lateinit var ijkMediaController: IjkMediaController

    private val mVideoViewModel: VideoViewModel by currentScope.viewModel(this)

    companion object {
        fun start(context: Context, content: ContentBean, videoListInfo: ArrayList<Content>, defaultIndex: Int = 0) {
            val bundle = Bundle()
            bundle.putSerializable(Extras.VIDEO_INFO, content)
            bundle.putSerializable(Extras.VIDEO_LIST_INFO, videoListInfo)
            bundle.putInt(Extras.VIDEO_INFO_INDEX, defaultIndex)
            val starter = Intent(context, VideoDetailActivity::class.java)
            starter.putExtras(bundle)
            context.startActivity(starter)
        }
    }

    override fun getBundleExtras(extras: Bundle?) {
        mCurrentVideoInfo = extras?.getSerializable(Extras.VIDEO_INFO) as ContentBean
        mVideoListInfo = extras.getSerializable(Extras.VIDEO_LIST_INFO) as ArrayList<Content>
        mCurrentIndex = extras.getInt(Extras.VIDEO_INFO_INDEX, 0)
    }


    override fun initView(savedInstanceState: Bundle?) {
        initPlaceHolder()
        initMediaController()
        initSeekBar()
        registerProgressEvent()
        playVideo()
        bindObserve()
    }

    private fun bindObserve() {
        mVideoViewModel.observeRelativeVideoInfo()
                .autoDispose(this)
                .subscribe(this::onRelativeVideoInfoStateArrive)
    }

    private fun onRelativeVideoInfoStateArrive(viewState: ViewState<AndyInfo>) {
        when (viewState.action) {
            Action.INIT_SUCCESS -> {
                getRelatedVideoInfoSuccess(viewState.data!!.itemList)
            }
            Action.INIT_FAIL -> {
                getRelatedVideoFail()
            }
        }
    }

    private fun initPlaceHolder() {
        with(mDataBinding) {
            videoViewWrapper.setPlaceImageUrl(mCurrentVideoInfo.cover.detail)
            ivBlurred.setImageURI(mCurrentVideoInfo.cover.blurred)
        }
    }

    private fun initMediaController() {
        ijkMediaController = IjkMediaController(this)
        ijkMediaController.initData(mCurrentIndex, mVideoListInfo.size, mCurrentVideoInfo)
        ijkMediaController.controllerListener = object : IjkMediaController.ControllerListener {
            override fun onBackClick() {
                finish()
            }

            override fun onNextClick() {
                mCurrentIndex = ++mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                refreshVideo(getFutureVideo())
            }

            override fun onFullScreenClick() {
                mDataBinding.videoViewWrapper.enterFullScreen()
            }

            override fun onTinyScreenClick() {
                mDataBinding.videoViewWrapper.exitFullScreen()
            }

            override fun onPreClick() {
                mCurrentIndex = --mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                refreshVideo(getFutureVideo())
            }

            override fun onErrorViewClick() {
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }

            override fun onShowController(isShowController: Boolean) {
                mDataBinding.seekBar.thumb = if (isShowController)
                    getDrawable(R.drawable.ic_player_progress_handle)
                else ColorDrawable(Color.TRANSPARENT)
            }

        }
    }


    private fun initSeekBar() {
        mDataBinding.seekBar.thumb = ColorDrawable(Color.TRANSPARENT)
        mDataBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(bar: SeekBar) {
                //控制的时候停止更新进度条，同时禁止隐藏
                mDataBinding.videoViewWrapper.setDragging(true)
                mDataBinding.videoViewWrapper.showControllerAllTheTime()

            }

            override fun onProgressChanged(bar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    //更新当前播放时间
                    mChangeProgress = progress
                }
            }

            override fun onStopTrackingTouch(bar: SeekBar) {
                //定位都拖动位置
                val newPosition = mDataBinding.videoViewWrapper.getDuration() * mChangeProgress / 1000L
                mDataBinding.videoViewWrapper.seekTo(newPosition.toInt())
                mDataBinding.videoViewWrapper.showController()
                mDataBinding.videoViewWrapper.setDragging(false)
            }
        })
    }


    /**
     * 重置视频信息
     */
    private fun refreshVideo(videoInfo: Content?) {
        videoInfo?.let {

            with(mDataBinding) {

                mCurrentVideoInfo = videoInfo.data
                rvVideoRecycler.visibility = View.INVISIBLE
                videoViewWrapper.togglePlaceImage(true)
                seekBar.secondaryProgress = 0
                seekBar.progress = 0

                initPlaceHolder()

                //重置视频播放信息
                videoViewWrapper.setVideoPath(mCurrentVideoInfo.playUrl)
                videoViewWrapper.start()
                //获取关联视频信息
                mVideoViewModel.getRelatedVideoInfo(mCurrentVideoInfo.id)
            }
        }

    }

    /**
     * 获取即将展示的视频
     */
    private fun getFutureVideo(): Content {
        return if (mVideoListInfo[mCurrentIndex].data.content != null) {
            mVideoListInfo[mCurrentIndex].data.content!!
        } else {
            mVideoListInfo[mCurrentIndex]
        }

    }

    private fun registerProgressEvent() {
        //注册进度条监听
        RxBus.register(this, VideoProgressEvent::class.java, Consumer {
            //如果正在拖动的话，不更新进度条
            if (!mDataBinding.videoViewWrapper.isDragging()) {
                mDataBinding.seekBar.progress = it.progress
            }
            mDataBinding.seekBar.secondaryProgress = it.secondaryProgress
        })

    }


    /**
     * 播放视频
     */
    private fun playVideo() {
        with(mDataBinding.videoViewWrapper) {
            //设置视频准备完成监听
            setOnPreparedListener(IMediaPlayer.OnPreparedListener {
                resetType()
                hidePlaceImage()
            })

            //设置视频播放失败监听
            setOnErrorListener(IMediaPlayer.OnErrorListener { _, _, _ ->
                showErrorView()
                true
            })

            //设置视频播放完成监听
            setOnCompletionListener(IMediaPlayer.OnCompletionListener {
                mDataBinding.seekBar.thumb = null
            })
            //设置视频地址，并开始播放
            setVideoPath(mCurrentVideoInfo.playUrl)
            toggleAspectRatio(IRenderView.AR_MATCH_PARENT)
            setMediaController(ijkMediaController)
            start()
        }
        //获取相关视频信息
        mVideoViewModel.getRelatedVideoInfo(mCurrentVideoInfo.id)
    }


    /**
     * 添加标题
     */
    private fun getVideoDetailView(): View {
        return VideoDetailHeadView(this).apply {
            setTitle(mCurrentVideoInfo.title)
            setCategoryAndTime(mCurrentVideoInfo.category, mCurrentVideoInfo.duration)
            setFavoriteCount(mCurrentVideoInfo.consumption.collectionCount)
            setShareCount(mCurrentVideoInfo.consumption.replyCount)
            setReplayCount(mCurrentVideoInfo.consumption.replyCount)
            setDescription(mCurrentVideoInfo.description)
            startScrollAnimation()
        }

    }

    /**
     * 设置相关视频信息
     */
    private fun getRelationVideoInfo(): View {
        return VideoDetailAuthorView(this).apply { setVideoAuthorInfo(mCurrentVideoInfo.author) }
    }

    private fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>) {
        with(mDataBinding.rvVideoRecycler) {
            visibility = View.VISIBLE

            layoutManager = LinearLayoutManager(this@VideoDetailActivity)

            adapter = VideoDetailAdapter(itemList).apply {
                addHeaderView(getVideoDetailView())
                addHeaderView(getRelationVideoInfo())
                openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                setFooterView(LayoutInflater.from(this@VideoDetailActivity).inflate(R.layout.item_the_end, null))

                setOnItemClickListener { _, _, position ->
                    if (getItemViewType(position) != BaseQuickAdapter.HEADER_VIEW) {
                        refreshVideo(getItem(position))
                    }
                }
            }
        }
    }

    private fun getRelatedVideoFail() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mBackPressed = true

    }


    override fun onResume() {
        super.onResume()
        //当前界面可见的时候重新注册进度条监听
        registerProgressEvent()
    }

    override fun onPause() {
        super.onPause()
        if (mDataBinding.videoViewWrapper.isPlaying()) {
            mDataBinding.videoViewWrapper.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mBackPressed) {
            mDataBinding.videoViewWrapper.stopPlayback()
            mDataBinding.videoViewWrapper.release(true)
        }
        RxBus.unRegister(this)
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition(): TransitionMode = TransitionMode.BOTTOM

    override fun getContentViewLayoutId() = R.layout.activity_video_detail

}