package com.jennifer.andy.simpleeyes.player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jennifer.andy.simpleeyes.R;

import java.util.Formatter;

/**
 * Author:  andy.xwt
 * Date:    2018/1/9 22:29
 * Description:
 */


public class IjkMediaController extends FrameLayout {

    private android.widget.MediaController.MediaPlayerControl mPlayer;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mDecorLayoutParams;
    private Window mWindow;
    private View mDecor;
    private View mRoot;

    private boolean mShowing;
    private View mAnchor;
    private boolean mDragging;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;

    private static final int sDefaultTimeout = 3000;

    private final Context mContext;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;


    public IjkMediaController(@NonNull Context context) {
        super(context);
        mContext = context;
        mRoot = this;
        initFloatingWindowLayout();
        initFloatingWindow();
    }


    /**
     * 初始化悬浮window布局
     */
    private void initFloatingWindowLayout() {
        mDecorLayoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.gravity = Gravity.TOP | Gravity.LEFT;
        p.height = LayoutParams.WRAP_CONTENT;
        p.x = 0;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
        p.token = null;
        p.windowAnimations = 0; // android.R.style.DropDownAnimationDown;
    }

    /**
     * 初始化悬浮window
     */
    private void initFloatingWindow() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindow = PolicyCompat.createPhoneWindow(mContext);
        mWindow.setWindowManager(mWindowManager, null, null);
        mWindow.requestFeature(Window.FEATURE_NO_TITLE);
        mDecor = mWindow.getDecorView();
        mDecor.setOnTouchListener(mTouchListener);
        mWindow.setContentView(this);
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);

        // While the media controller is up, the volume control keys should
        // affect the media stream type
        mWindow.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        requestFocus();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null) {
            initControllerView(mRoot);
        }
    }

    /**
     * 初始化控制层视图
     */
    private void initControllerView(View root) {

    }

    /**
     * 触摸监听，如果按下且正在播放，隐藏
     */
    private final OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mShowing) {
                    hide();
                }
            }
            return false;
        }
    };

    /**
     * 设置进度条
     */
    private int setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null)
            mEndTime.setText(stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    /**
     * 显示进度条线程
     */
    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (!mDragging && mShowing && mPlayer.isPlaying()) {
                postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    /**
     * 格式化时间
     */
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 把控制器移除屏幕
     */
    public void hide() {
        if (mAnchor == null)
            return;

        if (mShowing) {
            try {
                removeCallbacks(mShowProgress);
                mWindowManager.removeView(mDecor);
            } catch (IllegalArgumentException ex) {
                Log.w("MediaController", "already removed");
            }
            mShowing = false;
        }
    }


    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
    }


    private void updatePausePlay() {
//        if (mRoot == null || mPauseButton == null)
//            return;
//
//        if (mPlayer.isPlaying()) {
//            mPauseButton.setImageResource(com.android.internal.R.drawable.ic_media_pause);
//            mPauseButton.setContentDescription(mPauseDescription);
//        } else {
//            mPauseButton.setImageResource(com.android.internal.R.drawable.ic_media_play);
//            mPauseButton.setContentDescription(mPlayDescription);
//        }
    }

    /**
     * Set the view that acts as the anchor for the control view.
     * This can for example be a VideoView, or your Activity's main view.
     * When VideoView calls this method, it will use the VideoView's parent
     * as the anchor.
     *
     * @param view The view to which to anchor the controller when it is visible.
     */
    public void setAnchorView(View view) {
//        if (mAnchor != null) {
//            mAnchor.removeOnLayoutChangeListener(mLayoutChangeListener);
//        }
//        mAnchor = view;
//        if (mAnchor != null) {
//            mAnchor.addOnLayoutChangeListener(mLayoutChangeListener);
//        }

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }

    /**
     * 创建控制层View
     */
    private View makeControllerView() {
        mRoot = LayoutInflater.from(mContext).inflate(R.layout.layout_media_controller, null);
        initControllerView(mRoot);
        return mRoot;
    }

    /**
     * Show the controller on screen. It will go away
     * automatically after 3 seconds of inactivity.
     */
    public void show() {
        show(sDefaultTimeout);
    }

    /**
     * 将控制器显示在屏幕上，当到达过期时间时会自动消失。
     *
     * @param timeout 过期时间(毫秒) 如果设置为0直到调用hide方法才会消失
     */
    public void show(int timeout) {
//        if (!mShowing && mAnchor != null) {
//            setProgress();
//            if (mPauseButton != null) {
//                mPauseButton.requestFocus();
//            }
//            disableUnsupportedButtons();
//            updateFloatingWindowLayout();
//            mWindowManager.addView(mDecor, mDecorLayoutParams);
//            mShowing = true;
//        }
//        updatePausePlay();
//
//        // cause the progress bar to be updated even if mShowing
//        // was already true.  This happens, for example, if we're
//        // paused with the progress bar showing the user hits play.
//        post(mShowProgress);
//
//        if (timeout != 0 && !mAccessibilityManager.isTouchExplorationEnabled()) {
//            removeCallbacks(mFadeOut);
//            postDelayed(mFadeOut, timeout);
//        }
    }

    public boolean isShowing() {
        return mShowing;
    }

}
