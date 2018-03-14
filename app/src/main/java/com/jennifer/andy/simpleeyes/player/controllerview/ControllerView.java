package com.jennifer.andy.simpleeyes.player.controllerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;

import com.jennifer.andy.simpleeyes.player.IjkMediaController;
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent;
import com.jennifer.andy.simpleeyes.rx.RxBus;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    private StringBuilder mFormatBuilder = new StringBuilder();
    private boolean isDragging;//是否正在拖动

    private View mRootView;

    private Disposable mDisposable;


    public ControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, Context context) {
        super(context);
        mPlayer = player;
        mController = controller;
        mContext = context;
        mRootView = this;
        LayoutInflater.from(mContext).inflate(setControllerLayoutId(), this, true);
        initView(mRootView);
        initControllerListener();
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
        cancelProgressRunnable();//先取消之前的
        startProgressRunnable();
        updateTogglePauseUI(mPlayer.isPlaying());
    }

    /**
     * 进度与时间更新线程
     */
    protected void startProgressRunnable() {
        if (mDisposable == null || mDisposable.isDisposed()) {
            mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            int position = mPlayer.getCurrentPosition();
                            int duration = mPlayer.getDuration();
                            if (duration >= 0 && mPlayer.getBufferPercentage() > 0 && !isDragging) {
                                long progress = 1000L * position / duration;
                                int secondProgress = mPlayer.getBufferPercentage() * 10;
                                updateProgress((int) progress, secondProgress);
                                //发送进度
                                RxBus.INSTANCE.post(new VideoProgressEvent((int) progress, secondProgress));
                            }
                            updateTime(stringForTime(position), stringForTime(duration));
                        }
                    });

        }
    }

    /**
     * 取消进度与时间更新线程
     */
    public void cancelProgressRunnable() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
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
    public abstract void updateProgress(int progress, int secondaryProgress);

    /**
     * 更新当前视频播放时间
     *
     * @param currentTime 当前时间
     * @param endTime     结束时间
     */
    public abstract void updateTime(String currentTime, String endTime);

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
    public abstract void updateTogglePauseUI(boolean isPlaying);

    /**
     * 隐藏按钮
     */
    public abstract void hideNextButton();


    /**
     * 获取根布局
     */
    public View getRootView() {
        return mRootView;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }
}
