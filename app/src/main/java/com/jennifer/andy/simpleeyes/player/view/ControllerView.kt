package com.jennifer.andy.simpleeyes.player.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController.MediaPlayerControl
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.player.IjkMediaController
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.utils.stringForTime
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 23:00
 * Description:
 */

abstract class ControllerView(context: Context) : FrameLayout(context) {

    protected lateinit var mPlayer: MediaPlayerControl
    protected lateinit var mController: IjkMediaController
    protected var mCurrentVideoInfo: ContentBean? = null

    var isDragging = false//是否正在拖动
    private var mErrorView: ErrorView? = null
    private val mContentViews: MutableList<View> = ArrayList()

    abstract val layoutId: Int

    fun initControllerAndData(player: MediaPlayerControl, controller: IjkMediaController, currentVideoInfo: ContentBean?) {
        mPlayer = player
        mController = controller
        mCurrentVideoInfo = currentVideoInfo
        LayoutInflater.from(context).inflate(layoutId, this, true)
        initView()
        initData()
    }

    /**
     * 初始化布局
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 显示
     */
    fun display() {
        updateTogglePauseUI(mPlayer.isPlaying)
    }

    /**
     * 更新暂停切换UI
     *
     * @param isPlaying 是否播放
     */
    open fun updateTogglePauseUI(isPlaying: Boolean) {}

    /**
     * 更新当前视频播放进度
     *
     * @param progress          第一进度
     * @param secondaryProgress 第二进度
     */
    open fun updateProgress(progress: Int, secondaryProgress: Int) {}

    /**
     * 更新当前视频播放时间
     *
     * @param currentTime 当前时间
     * @param endTime     结束时间
     */
    open fun updateTime(currentTime: String, endTime: String) {}

    /**
     * 根据传入的[videoProgressEvent] 更新播放进度与时间
     *
     * @param videoProgressEvent 播放进度事件
     */
    fun updateProgressAndTime(videoProgressEvent: VideoProgressEvent) {
        if (!isDragging) { //没在拖动的时候跟新进度条
            updateProgress(videoProgressEvent.progress, videoProgressEvent.secondaryProgress)
            updateTime(stringForTime(videoProgressEvent.currentPosition), stringForTime(videoProgressEvent.duration))
        }
    }

    /**
     * 暂停切换
     */
    fun togglePause() {
        if (mPlayer.isPlaying) {
            mPlayer.pause()
        } else {
            mPlayer.start()
        }
        updateTogglePauseUI(mPlayer.isPlaying)
    }


    internal enum class State {
        ERROR
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        if (child != null && child.tag !== State.ERROR) mContentViews.add(child)
    }

    /**
     * 显示错误界面
     */
    fun showErrorView() {
        for (contentView in mContentViews) {
            contentView.visibility = View.GONE
        }
        if (mErrorView == null) {
            mErrorView = ErrorView(context).apply {
                setController(mController)
                tag = State.ERROR
            }
            addView(mErrorView)
        } else {
            mErrorView!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示内容界面，默认会将错误界面隐藏
     */
    fun showContent() {
        for (contentView in mContentViews) {
            contentView.visibility = View.VISIBLE
        }
        if (mErrorView != null) {
            mErrorView!!.visibility = View.GONE
        }
    }

}
