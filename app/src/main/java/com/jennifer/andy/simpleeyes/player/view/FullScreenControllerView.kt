package com.jennifer.andy.simpleeyes.player.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.stringForTime
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 23:17
 * Description:
 */

class FullScreenControllerView(context: Context) : ControllerView(context), View.OnClickListener {

    private lateinit var mMinScreen: ImageView
    private lateinit var mPreButton: ImageView
    private lateinit var mPauseButton: ImageView
    private lateinit var mNextButton: ImageView
    private lateinit var mProgress: SeekBar
    private lateinit var mTitle: CustomFontTextView
    private lateinit var mEndTime: CustomFontTextView
    private lateinit var mCurrentTime: CustomFontTextView

    private var mChangeProgress = 0

    companion object {
        const val FULL_SCREEN_ID = 100
    }

    override val layoutId = R.layout.layout_media_controller_full_screen

    override fun initView() {
        mMinScreen = findViewById(R.id.iv_min_screen)
        mProgress = findViewById(R.id.sb_progress)
        mTitle = findViewById(R.id.tv_title)
        mPreButton = findViewById(R.id.iv_previous)
        mPauseButton = findViewById(R.id.iv_pause)
        mNextButton = findViewById(R.id.iv_next)
        mProgress = findViewById(R.id.sb_progress)
        mCurrentTime = findViewById(R.id.tv_currentTime)
        mEndTime = findViewById(R.id.tv_end_time)
        initListener()
    }

    private fun initListener() {
        mPreButton.setOnClickListener(this)
        mMinScreen.setOnClickListener(this)
        mPauseButton.setOnClickListener(this)
        mNextButton.setOnClickListener(this)
        setOnClickListener(this)

        mProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(bar: SeekBar) { //控制的时候停止更新进度条，同时禁止隐藏
                isDragging = true
                mController.showAllTheTime()
            }

            override fun onProgressChanged(bar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) { //更新当前播放时间
                    val duration = mPlayer.duration.toLong()
                    val newPosition = duration * progress / 1000L
                    mChangeProgress = progress
                    mCurrentTime.text = stringForTime(newPosition.toInt())
                }
            }

            override fun onStopTrackingTouch(bar: SeekBar) { //定位都拖动位置
                val newPosition = mPlayer.duration * mChangeProgress / 1000L
                mPlayer.seekTo(newPosition.toInt())
                mController.show() //开启延时隐藏
                isDragging = false
            }
        })
    }

    override fun initData() {
        //初始化标题
        mTitle.text = mCurrentVideoInfo!!.title

        //初始化播放时间
        val position = mPlayer.currentPosition
        val duration = mPlayer.duration

        mCurrentTime.text = stringForTime(position)
        mEndTime.text = stringForTime(duration)

        //初始化进度条
        mProgress.apply {
            max = 1000
            if (duration >= 0 && mPlayer.bufferPercentage > 0) {
                val firstProgress = 1000L * position / duration
                val secondProgress = mPlayer.bufferPercentage * 10
                progress = firstProgress.toInt()
                secondaryProgress = secondProgress
            }
            setPadding(0, 0, 0, 0)
        }
        updatePreNextButton()
        id = FULL_SCREEN_ID
    }


    //判断是否显示上一个按钮与下一个按钮
    private fun updatePreNextButton() {
        mPreButton.visibility = if (mController.isHavePreVideo()) View.VISIBLE else View.GONE
        mNextButton.visibility = if (mController.isHaveNextVideo()) View.VISIBLE else View.GONE
    }


    override fun onClick(v: View) {
        when (v.id) {
            FULL_SCREEN_ID -> mController.hide()
            R.id.iv_pause -> {
                togglePause()
                mController.show()
            }
            R.id.iv_previous -> {
                mController.controllerListener?.onPreClick()
                updatePreNextButton()
            }
            R.id.iv_next -> {
                mController.controllerListener?.onNextClick()
                updatePreNextButton()
            }
            R.id.iv_min_screen -> {
                mController.switchControllerView(ControllerViewFactory.TINY_MODE)
                mController.controllerListener?.onTinyScreenClick()
            }
        }
    }

    override fun updateProgress(progress: Int, secondaryProgress: Int) {
        mProgress.progress = progress
        mProgress.secondaryProgress = secondaryProgress
    }

    override fun updateTime(currentTime: String, endTime: String) {
        mCurrentTime.text = currentTime
        mEndTime.text = endTime
    }

    override fun updateTogglePauseUI(isPlaying: Boolean) {
        if (isPlaying) {
            mPauseButton.setImageResource(R.drawable.ic_player_pause)
        } else {
            mPauseButton.setImageResource(R.drawable.ic_player_play)
        }
    }
}
