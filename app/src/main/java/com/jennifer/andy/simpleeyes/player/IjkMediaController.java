package com.jennifer.andy.simpleeyes.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jennifer.andy.simpleeyes.R;

import java.util.Formatter;
import java.util.Locale;

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
    private boolean mDragging;


    private View mAnchor;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;

    private static final int sDefaultTimeout = 3500;

    private final Context mContext;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;

    private ImageView mPauseButton;
    private ImageView mNextButton;
    private View.OnClickListener mNextListener, mPrevListener;
    private boolean mListenersSet;

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

    //动态更新根布局的高度与宽度，注意：需要mAnchor != NULL
    private void updateFloatingWindowLayout() {
        int[] anchorPos = new int[2];
        mAnchor.getLocationOnScreen(anchorPos);

        // we need to know the size of the controller so we can properly position it
        // within its space
        mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(mAnchor.getHeight(), MeasureSpec.AT_MOST));

        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.width = mAnchor.getWidth();
        p.x = anchorPos[0] + (mAnchor.getWidth() - p.width) / 2;
        p.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null) {
            initControllerView(mRoot);
        }
    }

    // 当锚点view布局发生改变的时候会调用
    private final OnLayoutChangeListener mLayoutChangeListener =
            new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right,
                                           int bottom, int oldLeft, int oldTop, int oldRight,
                                           int oldBottom) {
                    updateFloatingWindowLayout();
                    if (mShowing) {
                        mWindowManager.updateViewLayout(mDecor, mDecorLayoutParams);
                    }
                }
            };

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


    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    private OnProgressChangeListener mOnProgressChangeListener;

    public interface OnProgressChangeListener {
        void onProgressChanged(int progress, int secondaryProgress);
    }

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
            mEndTime.setText("/" + stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        if (mOnProgressChangeListener != null && !mDragging) {
            mOnProgressChangeListener.onProgressChanged((int) (1000L * position / duration), mPlayer.getBufferPercentage() * 10);
        }
        return position;
    }

    /**
     * 显示进度条线程
     */
    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (!mDragging && mPlayer.isPlaying()) {
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
     * 隐藏控制器
     */
    public void hide() {
        if (mAnchor == null)
            return;
        if (mShowing) {
            try {
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

    /**
     * 更新暂停按钮显示内容，播放->暂停  暂停->播放
     */
    private void updatePausePlay() {
        if (mRoot == null || mPauseButton == null)
            return;
        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(R.drawable.ic_player_pause);
        } else {
            mPauseButton.setImageResource(R.drawable.ic_player_play);
        }
    }

    /**
     * 将控制层view与视屏播放view进行关联，并且将视屏播放view添加进控制层view
     */
    public void setAnchorView(View view) {
        if (mAnchor != null) {
            mAnchor.removeOnLayoutChangeListener(mLayoutChangeListener);
        }
        mAnchor = (View) view.getParent();
        if (mAnchor != null) {
            mAnchor.addOnLayoutChangeListener(mLayoutChangeListener);
        }

        ViewGroup.LayoutParams mAnchorLayoutParams = mAnchor.getLayoutParams();
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                mAnchorLayoutParams.width, mAnchorLayoutParams.height);
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

    ///////////////////////////////////////////////////////////////////////////
    // 控制层监听
    ///////////////////////////////////////////////////////////////////////////
    private final View.OnClickListener mPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);

            mDragging = true;
            //控制的时候停止更新进度条，同时禁止隐藏
            removeCallbacks(mShowProgress);
            removeCallbacks(mFadeOut);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }

            long duration = mPlayer.getDuration();
            long newposition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newposition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);

            //拖动结束的时候，更新进度条，开始隐藏
            post(mShowProgress);
            post(mFadeOut);
        }
    };


    /**
     * 如果正在播放就停止
     */
    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();//更新暂停按钮
    }


    /**
     * 初始化控制层视图
     */
    private void initControllerView(View root) {

        mPauseButton = root.findViewById(R.id.iv_pause);
        if (mPauseButton != null) {
            mPauseButton.setOnClickListener(mPauseListener);
        }
        mNextButton = root.findViewById(R.id.iv_next);
        if (mNextButton != null) {

        }

        mProgress = root.findViewById(R.id.sb_progress);
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.setOnSeekBarChangeListener(mSeekListener);
            }
            mProgress.setPadding(0, 0, 0, 0);
            mProgress.setMax(1000);
        }
        //总时间与当前时间
        mCurrentTime = root.findViewById(R.id.tv_currentTime);
        mEndTime = root.findViewById(R.id.tv_end_time);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * 显示当前控制层view，3秒之后会自动消失
     */
    public void show() {
        show(sDefaultTimeout);
    }

    /**
     * 第一次显示，不显示控制层
     */
    public void firstShow() {
        if (!mShowing && mAnchor != null) {
            setProgress();
            post(mShowProgress);
        }
    }

    /**
     * 隐藏控制层view
     */
    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            if (!mDragging) {//如果正在拖动就不隐藏
                hide();
            }
        }
    };

    /**
     * 将控制器显示在屏幕上，当到达过期时间时会自动消失。
     *
     * @param timeout 过期时间(毫秒) 如果设置为0直到调用hide方法才会消失
     */
    public void show(int timeout) {
        if (!mShowing && mAnchor != null) {
            setProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            updateFloatingWindowLayout();
            mWindowManager.addView(mDecor, mDecorLayoutParams);
            mShowing = true;
        }
        updatePausePlay();

        // cause the progress bar to be updated even if mShowing
        // was already true.  This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
        post(mShowProgress);

        if (timeout != 0) {
            removeCallbacks(mFadeOut);
            postDelayed(mFadeOut, timeout);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
                removeCallbacks(mShowProgress);
                //如果传入的是activity直接退出
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public boolean isShowing() {
        return mShowing;
    }

}
