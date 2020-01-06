package com.jennifer.andy.simpleeyes.ui.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.base.rx.RxBus
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.player.IjkMediaController
import com.jennifer.andy.simpleeyes.player.IjkVideoView
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.player.render.IRenderView
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.adapter.VideoDetailAdapter
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoDetailPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoDetailView
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.widget.VideoDetailAuthorView
import com.jennifer.andy.simpleeyes.widget.pull.head.VideoDetailHeadView
import io.reactivex.functions.Consumer
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2017/12/18 16:51
 * Description: 视频详细信息界面
 */
class VideoDetailActivity : BaseActivity<VideoDetailView, VideoDetailPresenter>(), VideoDetailView {

    private val mPlaceImage by bindView<SimpleDraweeView>(R.id.iv_place_image)
    private val mBlurredImage by bindView<SimpleDraweeView>(R.id.iv_blurred)
    private val mVideoView by bindView<IjkVideoView>(R.id.video_view)
    private val mProgress by bindView<ProgressBar>(R.id.progress)
    private val mHorizontalProgress by bindView<ProgressBar>(R.id.sb_progress)
    private val mRecycler by bindView<RecyclerView>(R.id.rv_video_recycler)

    private lateinit var mCurrentVideoInfo: ContentBean
    private var mCurrentIndex = 0
    private lateinit var mVideoListInfo: MutableList<Content>
    private var mBackPressed = false

    private lateinit var ijkMediaController: IjkMediaController


    companion object {

        /**
         * 跳转到视频详细界面
         */
        @JvmStatic
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

    override fun getBundleExtras(extras: Bundle) {
        mCurrentVideoInfo = extras.getSerializable(Extras.VIDEO_INFO) as ContentBean
        mVideoListInfo = extras.getSerializable(Extras.VIDEO_LIST_INFO) as ArrayList<Content>
        mCurrentIndex = extras.getInt(Extras.VIDEO_INFO_INDEX, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initPlaceHolder()
        initMediaController()
        registerProgressEvent()
        playVideo()
    }


    private fun initPlaceHolder() {
        mPlaceImage.setImageURI(mCurrentVideoInfo.cover.detail)
        mBlurredImage.setImageURI(mCurrentVideoInfo.cover.blurred)
        mHorizontalProgress.max = 1000
    }


    private fun initMediaController() {
        ijkMediaController = IjkMediaController(mCurrentIndex, mVideoListInfo.size, mCurrentVideoInfo, mContext)
        ijkMediaController.controllerListener = object : IjkMediaController.ControllerListener {
            override fun onBackClick() {
                finish()
            }

            override fun onNextClick() {
                mCurrentIndex = ++mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }

            override fun onFullScreenClick() {
                mVideoView.enterFullScreen()
            }

            override fun onTinyScreenClick() {
                mVideoView.exitFullScreen()
            }

            override fun onPreClick() {
                mCurrentIndex = --mCurrentIndex
                ijkMediaController.currentIndex = mCurrentIndex
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }

            override fun onErrorViewClick() {
                ijkMediaController.hide()
                refreshVideo(getFutureVideo())
            }
        }
    }

    /**
     * 重置视频信息
     */
    private fun refreshVideo(videoInfo: Content?) {
        videoInfo?.let {
            mCurrentVideoInfo = videoInfo.data

            mRecycler.visibility = View.INVISIBLE
            mPlaceImage.visibility = View.VISIBLE
            mProgress.visibility = View.VISIBLE

            mHorizontalProgress.secondaryProgress = 0
            mHorizontalProgress.progress = 0

            initPlaceHolder()
            mVideoView.setVideoPath(mCurrentVideoInfo.playUrl)
            mVideoView.start()
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
            mHorizontalProgress.progress = it.progress
            mHorizontalProgress.secondaryProgress = it.secondaryProgress
        })

    }


    /**
     * 播放视频
     */
    private fun playVideo() {

        with(mVideoView) {

            //设置视频地址，并开始播放
            setVideoPath(mCurrentVideoInfo.playUrl)
            setMediaController(ijkMediaController)
            start()

            //设置准备完成监听
            setOnPreparedListener {
                //隐藏进度条
                mPlaceImage.handler.postDelayed({
                    mPlaceImage.visibility = View.GONE
                    mProgress.visibility = View.GONE
                }, 500)

                ijkMediaController.resetType()

            }

            toggleAspectRatio(IRenderView.AR_MATCH_PARENT)

            //设置失败监听
            setOnErrorListener { _, _, _ ->
                mProgress.handler.postDelayed({
                    mProgress.visibility = View.GONE
                    ijkMediaController.showErrorView()
                }, 1000)

            }
            //设置完成监听
            setOnCompletionListener {

            }
        }
        //获取相关视屏信息
        mPresenter.getRelatedVideoInfo(mCurrentVideoInfo.id)
    }


    /**
     * 添加标题
     */
    private fun getVideoDetailView(): View {
        return VideoDetailHeadView(mContext).apply {
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
        return VideoDetailAuthorView(mContext).apply { setVideoAuthorInfo(mCurrentVideoInfo.author) }
    }

    override fun getRelatedVideoInfoSuccess(itemList: MutableList<Content>) {

        with(mRecycler) {
            visibility = View.VISIBLE

            layoutManager = LinearLayoutManager(mContext)

            adapter = VideoDetailAdapter(itemList).apply {
                addHeaderView(getVideoDetailView())
                addHeaderView(getRelationVideoInfo())
                openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                setFooterView(LayoutInflater.from(mContext).inflate(R.layout.item_the_end, null))

                setOnItemClickListener { _, _, position ->
                    if (getItemViewType(position) != BaseQuickAdapter.HEADER_VIEW) {
                        refreshVideo(getItem(position))
                    }
                }
            }
        }
    }

    override fun getRelatedVideoFail() {
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        mBackPressed = true
    }


    override fun onResume() {
        super.onResume()
        //当前界面可见的时候重新注册进度条监听
        registerProgressEvent()
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
            //如果视频没有加载出来，就直接退出的话，这里会出现卡顿的情况，官方也没有给出解释
            //issues: https://github.com/bilibili/ijkplayer/issues?utf8=✓&q=Anr
            mVideoView.stopPlayback()
            mVideoView.release(true)
        }
        RxBus.unRegister(this)
    }

    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition(): TransitionMode = TransitionMode.BOTTOM

    override fun getContentViewLayoutId() = R.layout.activity_video_detail

}