package com.jennifer.andy.simpleeyes.player.view

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.stringForTime


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 23:37
 * Description:
 */

class TinyControllerView(context: Context) : ControllerView(context), View.OnClickListener {

    private lateinit var mPauseButton: ImageView
    private lateinit var mPreButton: ImageView
    private lateinit var mBackButton: ImageView
    private lateinit var mNextButton: ImageView
    private lateinit var mFullScreen: ImageView

    private lateinit var mEndTime: TextView
    private lateinit var mCurrentTime: TextView

    override val layoutId = R.layout.layout_media_controller_tiny

    override fun initView() {
        mPauseButton = findViewById(R.id.iv_pause)
        mPreButton = findViewById(R.id.iv_previous)
        mBackButton = findViewById(R.id.iv_back)
        mNextButton = findViewById(R.id.iv_next)
        mFullScreen = findViewById(R.id.iv_full_screen)
        mEndTime = findViewById(R.id.tv_end_time)
        mCurrentTime = findViewById(R.id.tv_currentTime)
        initListener()
    }

    private fun initListener() {
        mPreButton.setOnClickListener(this)
        mPauseButton.setOnClickListener(this)
        mNextButton.setOnClickListener(this)
        mBackButton.setOnClickListener(this)
        mFullScreen.setOnClickListener(this)
    }

    override fun initData() { //初始化开始时间
        val position = mPlayer.currentPosition
        val duration = mPlayer.duration
        mCurrentTime.text = stringForTime(position)
        mEndTime.text = "/${stringForTime(duration)}"
        updatePreNextButton()
    }


    //判断是否显示上一个按钮与下一个按钮
    private fun updatePreNextButton() {
        mPreButton.visibility = if (mController.isHavePreVideo()) View.VISIBLE else View.GONE
        mNextButton.visibility = if (mController.isHaveNextVideo()) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
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
            R.id.iv_back -> {
                mController.hide()
                mController.controllerListener?.onBackClick()
            }
            R.id.iv_full_screen -> {
                mController.switchControllerView(ControllerViewFactory.FULL_SCREEN_MODE)
                mController.controllerListener?.onFullScreenClick()
            }
        }
    }

    override fun updateProgress(progress: Int, secondaryProgress: Int) {}


    override fun updateTime(currentTime: String, endTime: String) {
        mCurrentTime.text = currentTime
        mEndTime.text = "/$endTime"
    }

    override fun updateTogglePauseUI(isPlaying: Boolean) {
        if (isPlaying) {
            mPauseButton.setImageResource(R.drawable.ic_player_pause)
        } else {
            mPauseButton.setImageResource(R.drawable.ic_player_play)
        }
    }
}
