package com.jennifer.andy.simpleeyes.player.controllerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.jennifer.andy.simpleeyes.R;
import com.jennifer.andy.simpleeyes.entity.ContentBean;
import com.jennifer.andy.simpleeyes.player.IjkMediaController;

/**
 * Author:  andy.xwt
 * Date:    2018/3/13 16:34
 * Description:竖直小界面视频控制器
 */


public class TinyControllerView extends ControllerView implements View.OnClickListener {

    private ImageView mPauseButton;
    private ImageView mPreButton;
    private ImageView mBackButton;
    private ImageView mNextButton;
    private ImageView mFullScreen;

    private TextView mEndTime;
    private TextView mCurrentTime;


    public TinyControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, ContentBean currentVideoInfo, Context context) {
        super(player, controller, currentVideoInfo, context);
    }

    @Override
    public void initView(View rootView) {

        mPauseButton = rootView.findViewById(R.id.iv_pause);
        mPreButton = rootView.findViewById(R.id.iv_previous);
        mBackButton = rootView.findViewById(R.id.iv_back);
        mNextButton = rootView.findViewById(R.id.iv_next);
        mFullScreen = rootView.findViewById(R.id.iv_full_screen);
        mCurrentTime = rootView.findViewById(R.id.tv_currentTime);
        mEndTime = rootView.findViewById(R.id.tv_end_time);

        //初始化开始时间
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        mCurrentTime.setText(stringForTime(position));
        mEndTime.setText("/" + stringForTime(duration));

        updatePreNextButton();

    }

    //判断是否显示上一个按钮与下一个按钮
    private void updatePreNextButton() {
        mPreButton.setVisibility(mController.isHavePreVideo() ? View.VISIBLE : View.GONE);
        mNextButton.setVisibility(mController.isHaveNextVideo() ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pause://暂停按钮
                togglePause();
                mController.show();
                break;
            case R.id.iv_previous://上一个
                mController.getControllerListener().onPreClick();
                updatePreNextButton();
                break;
            case R.id.iv_next://下一个按钮
                mController.getControllerListener().onNextClick();
                updatePreNextButton();
                break;
            case R.id.iv_back://回退键
                mController.hide();
                mController.getControllerListener().onBackClick();
                break;
            case R.id.iv_full_screen://全屏按钮
                mController.toggleControllerView(new FullScreenControllerView(mPlayer, mController, mCurrentVideoInfo, getContext()));
                break;

        }

    }

    @Override
    public void initControllerListener() {
        mPreButton.setOnClickListener(this);
        mPauseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);
    }

    @Override
    public void updateProgress(int progress, int secondaryProgress) {
    }

    @Override
    public void updateTime(String currentTime, String endTime) {
        mCurrentTime.setText(currentTime);
        mEndTime.setText("/" + endTime);
    }

    @Override
    public void updateTogglePauseUI(boolean isPlaying) {
        if (isPlaying) {
            mPauseButton.setImageResource(R.drawable.ic_player_pause);
        } else {
            mPauseButton.setImageResource(R.drawable.ic_player_play);
        }
    }


    @Override
    public int setControllerLayoutId() {
        return R.layout.layout_media_controller_tiny;
    }

}
