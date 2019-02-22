package com.jennifer.andy.simpleeyes.player.controllerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;

import com.jennifer.andy.simpleeyes.entity.ContentBean;
import com.jennifer.andy.simpleeyes.player.IjkMediaController;
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent;
import com.jennifer.andy.simpleeyes.rx.RxBus;

import java.util.Formatter;
import java.util.Locale;

import io.reactivex.functions.Consumer;

/**
 * Author:  andy.xwt
 * Date:    2018/3/13 15:33
 * Description: 封装控制层切换View
 */


public abstract class ControllerView extends FrameLayout {


    protected Context mContext;
    protected MediaController.MediaPlayerControl mPlayer;
    protected IjkMediaController mController;
    protected ContentBean mCurrentVideoInfo;

    private StringBuilder mFormatBuilder = new StringBuilder();

    private View mRootView;
    private boolean isDragging;//是否正在拖动


    public ControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, ContentBean currentVideoInfo, Context context) {
        super(context);
        mPlayer = player;
        mController = controller;
        mCurrentVideoInfo = currentVideoInfo;
        mContext = context;
        mRootView = this;
        LayoutInflater.from(mContext).inflate(setControllerLayoutId(), this, true);
        initView(mRootView);
        initControllerListener();
        RxBus.INSTANCE.register(this, VideoProgressEvent.class, new Consumer<VideoProgressEvent>() {
            @Override
            public void accept(VideoProgressEvent videoProgressEvent) throws Exception {
                if (!isDragging) {//没在拖动的时候跟新进度条
                    updateProgress(videoProgressEvent.getProgress(), videoProgressEvent.getSecondaryProgress());
                    updateTime(stringForTime(videoProgressEvent.getCurrentPosition()), stringForTime(videoProgressEvent.getDuration()));
                }
            }
        });

    }

    /**
     * 初始化控制层布局
     */
    public abstract void initView(View rootView);

    /**
     * 获取控制层布局id
     */
    public abstract int setControllerLayoutId();


    /**
     * 初始化控制层监听
     */
    public abstract void initControllerListener();

    /**
     * 开始播放
     */
    public void show() {
        updateTogglePauseUI(mPlayer.isPlaying());
    }


    /**
     * 格式化时间
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /**
     * 更新当前视频播放进度
     *
     * @param progress          第一进度
     * @param secondaryProgress 第二进度
     */
    public void updateProgress(int progress, int secondaryProgress) {
    }

    /**
     * 更新当前视频播放时间
     *
     * @param currentTime 当前时间
     * @param endTime     结束时间
     */
    public void updateTime(String currentTime, String endTime) {
    }

    /**
     * 暂停切换
     */
    public void togglePause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updateTogglePauseUI(mPlayer.isPlaying());
    }


    /**
     * 更新暂停切换UI
     *
     * @param isPlaying 是否播放
     */
    public void updateTogglePauseUI(boolean isPlaying) {
    }

    /**
     * 获取根布局
     */
    public View getRootView() {
        return mRootView;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RxBus.INSTANCE.unRegister(this);

    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }


}
