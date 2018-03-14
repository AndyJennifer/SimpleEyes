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
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.ItemList
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.player.IjkMediaController
import com.jennifer.andy.simpleeyes.player.IjkVideoView
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.player.render.IRenderView
import com.jennifer.andy.simpleeyes.rx.RxBus
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.adapter.VideoDetailAdapter
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoDetailPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.VideoDetailAuthorView
import com.jennifer.andy.simpleeyes.widget.VideoDetailHeadView
import io.reactivex.functions.Consumer
import tv.danmaku.ijk.media.player.IjkMediaPlayer


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */

class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>(), VideoDetailView {

    private val mShareImage by bindView<SimpleDraweeView>(R.id.iv_place_image)
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)
    private val mProgress by bindView<ProgressBar>(R.id.progress)
    private val mHorizontalProgress by bindView<ProgressBar>(R.id.sb_progress)
    private val mRecycler by bindView<RecyclerView>(R.id.rv_video_recycler)

    private lateinit var mCurrentVideoInfo: Content
    private var mCurrentIndex = 0
    private lateinit var mVideoListInfo: MutableList<ItemList>
    private var mBackPressed = false

    private lateinit var mVideoDetailAdapter: VideoDetailAdapter
    private lateinit var ijkMediaController: IjkMediaController

    override fun getBundleExtras(extras: Bundle) {
        mCurrentVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as Content
        mVideoListInfo = extras.getSerializable(Extras.VIDEO_LIST_INFO) as MutableList<ItemList>
        mCurrentIndex = extras.getInt(Extras.VIDEO_INFO_INDEX, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initPlaceHolder()
        initMediaController()
        playVideo(mCurrentVideoInfo)
    }


    private fun initPlaceHolder() {
        mShareImage.setImageURI(mCurrentVideoInfo.data.cover.detail)
        mBlurredImage.setImageURI(mCurrentVideoInfo.data.cover.blurred)
    }

    /**
     * 播放视频
     */
    private fun playVideo(currentVideoInfo: Content) {
        mShareImage.visibility = View.VISIBLE
        mProgress.visibility = View.VISIBLE
        mVideoView.setVideoPath(currentVideoInfo.data.playUrl)
        mVideoView.setMediaController(ijkMediaController)
        mVideoView.start()
        //设置准备完成监听
        mVideoView.setOnPreparedListener {
            //隐藏进度条
            mShareImage.visibility = View.GONE
            mProgress.visibility = View.GONE
            //获取相关视屏信息
            mPresenter.getRelatedVideoInfo(currentVideoInfo.data.id)
        }
        mVideoView.toggleAspectRatio(IRenderView.AR_MATCH_PARENT)

        //设置完成监听
        mVideoView.setOnCompletionListener {
            // todo 完成后更改布局

        }

    }

    /**
     * 添加标题
     */
    private fun getVideoDetailView(): View {
        val view = VideoDetailHeadView(mContext)
        with(view) {
            setTitle(mCurrentVideoInfo.data.title)
            setCategoryAndTime(mCurrentVideoInfo.data.category, mCurrentVideoInfo.data.duration)
            setFavoriteCount(mCurrentVideoInfo.data.consumption.collectionCount)
            setShareCount(mCurrentVideoInfo.data.consumption.replyCount)
            setReplayCount(mCurrentVideoInfo.data.consumption.replyCount)
            setDescription(mCurrentVideoInfo.data.description)
        }
        view.startScrollAnimation()
        return view
    }

    /**
     * 设置相关视频信息
     */
    private fun getRelationVideoInfo(): View {
        val view = VideoDetailAuthorView(mContext)
        view.setVideoAuthorInfo(mCurrentVideoInfo.data.author)
        return view
    }

    override fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>) {
        mRecycler.visibility = View.VISIBLE
        mVideoDetailAdapter = VideoDetailAdapter(itemList)
        mVideoDetailAdapter.addHeaderView(getVideoDetailView())
        mVideoDetailAdapter.addHeaderView(getRelationVideoInfo())
        mVideoDetailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mVideoDetailAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.item_the_end, null))
        mVideoDetailAdapter.setOnItemClickListener { _, _, position ->
            if (mVideoDetailAdapter.getItemViewType(position) != BaseQuickAdapter.HEADER_VIEW) {
                val item = mVideoDetailAdapter.getItem(position)
                refreshVideo(item!!)
            }
        }
        mRecycler.layoutManager = LinearLayoutManager(mContext)
        mRecycler.adapter = mVideoDetailAdapter
    }

    override fun getRelatedVideoFail() {

    }

    private fun initMediaController() {
        mHorizontalProgress.max = 1000
        ijkMediaController = IjkMediaController(mContext)

        if (mCurrentIndex == mVideoListInfo.size) {
            ijkMediaController.hide()
        }

        RxBus.register(this, VideoProgressEvent::class.java, Consumer {
            mHorizontalProgress.progress = it.progress
            mHorizontalProgress.secondaryProgress = it.secondaryProgress

            println("当前的进度${it.progress}+第二进度${it.secondaryProgress}")
        })


        ijkMediaController.controllerListener = object : IjkMediaController.ControllerListener {
            override fun onBackClick() {
                finish()
            }

            override fun onNextClick() {
                //先判断当前角标的位置，然后判断是否有下一页
                if (mCurrentIndex < mVideoListInfo.size) {
                    refreshVideo(mVideoListInfo[++mCurrentIndex].data.content)

                } else {
                    //否则则隐藏下一页
                    ijkMediaController.hideNextButton()
                }
            }

            override fun onFullScreenClick() {
                mVideoView.enterFullScreen()
            }

            override fun onTinyScreenClick() {
                mVideoView.exitFullScreen()
            }
        }
    }

    /**
     * 重置视频信息
     */
    private fun refreshVideo(videoInfo: Content) {
        mCurrentVideoInfo = videoInfo
        if (mVideoView.isPlaying) {
            mVideoView.stopPlayback()
            mVideoView.release(true)
        }
        mRecycler.visibility = View.INVISIBLE
        initView(null)
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

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition(): TransitionMode = TransitionMode.BOTTOM

    override fun getContentViewLayoutId() = R.layout.activity_video_detail

}