package com.jennifer.andy.simpleeyes.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;

import com.jennifer.andy.simpleeyes.entity.ContentBean;
import com.jennifer.andy.simpleeyes.player.controllerview.ControllerView;
import com.jennifer.andy.simpleeyes.player.controllerview.ErrorControllerView;
import com.jennifer.andy.simpleeyes.player.controllerview.FullScreenControllerView;
import com.jennifer.andy.simpleeyes.player.controllerview.TinyControllerView;
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent;

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

    private boolean mShowing;
    private View mAnchor;

    private final Context mContext;
    private ControllerView mControllerView;

    private ContentBean mCurrentVideoInfo;
    private int mTotalCount;
    private int mCurrentIndex;

    private int mCurrentViewState = TINY_VIEW;//默认情况下是小试图
    private static final int TINY_VIEW = 0;
    private static final int FULL_SCREEN_VIEW = 1;
    private static final int ERROR_VIEW = 2;

    private LayoutParams mTinyParams;
    private LayoutParams mFullParams;

    private static final int sDefaultTimeout = 3500;//默认消失时间 3.5秒
    private ErrorControllerView mErrorView;

    /**
     * @param currentIndex     当前视频角标
     * @param totalCount       视频集合总数
     * @param currentVideoInfo 当前视频信息
     */
    public IjkMediaController(int currentIndex, int totalCount, ContentBean currentVideoInfo, @NonNull Context context) {
        super(context);
        mCurrentIndex = currentIndex;
        mTotalCount = totalCount;
        mCurrentVideoInfo = currentVideoInfo;
        mContext = context;
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

        mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(mAnchor.getHeight(), MeasureSpec.AT_MOST));

        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.width = mAnchor.getWidth();
        p.x = anchorPos[0] + (mAnchor.getWidth() - p.width) / 2;
        p.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();


        removeAllViews();
        ViewGroup.LayoutParams mAnchorLayoutParams = mAnchor.getLayoutParams();
        if (mCurrentViewState == TINY_VIEW) {
            addTinyView(mAnchorLayoutParams);
        } else if (mCurrentViewState == FULL_SCREEN_VIEW) {
            addFullScreenView();
        } else {
            addErrorView();
        }


    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mControllerView.initControllerListener();
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
                    removeCallbacks(mFadeOut);
                    postDelayed(mFadeOut, sDefaultTimeout);
                }
            }
            return false;
        }
    };


    public void setMediaPlayer(MediaController.MediaPlayerControl player) {
        mPlayer = player;
        if (mControllerView != null) {
            mControllerView.togglePause();
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

        removeAllViews();
        ViewGroup.LayoutParams mAnchorLayoutParams = mAnchor.getLayoutParams();
        if (mCurrentViewState == TINY_VIEW) {
            addTinyView(mAnchorLayoutParams);
        } else if (mCurrentViewState == FULL_SCREEN_VIEW) {
            addFullScreenView();
        } else {
            addErrorView();
        }

    }

    /**
     * 添加错误界面
     */
    private void addErrorView() {
        mErrorView = new ErrorControllerView(mPlayer, this, mCurrentVideoInfo, mContext);
        if (mControllerView instanceof TinyControllerView) {
            addView(mErrorView, mTinyParams);
        } else {
            ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(mErrorView, layoutParams);
        }
    }


    /**
     * 添加竖直控制层
     */
    private void addTinyView(ViewGroup.LayoutParams mAnchorLayoutParams) {
        mTinyParams = new LayoutParams(mAnchorLayoutParams.width, mAnchorLayoutParams.height);
        if (mControllerView == null) {
            mControllerView = new TinyControllerView(mPlayer, this, mCurrentVideoInfo, mContext);
        }
        addView(mControllerView.getRootView(), mTinyParams);
    }

    /**
     * 添加全屏界面
     */
    private void addFullScreenView() {
        mFullParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mControllerView == null) {
            mControllerView = new FullScreenControllerView(mPlayer, this, mCurrentVideoInfo, mContext);
        }
        addView(mControllerView.getRootView(), mFullParams);
    }


    /**
     * 隐藏控制层view
     */
    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            if (mShowing) {
                hide();
            }
        }
    };

    /**
     * 第一次显示，不显示控制层
     */
    public void firstShow() {
        if (!mShowing && mAnchor != null) {
            mControllerView.show();
        }
    }

    /**
     * 显示当前控制层view，默认时间内会自动消失
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
        if (!mShowing && mAnchor != null) {
            updateFloatingWindowLayout();
            mWindowManager.addView(mDecor, mDecorLayoutParams);
            mShowing = true;
        }
        if (timeout != 0) {
            removeCallbacks(mFadeOut);
            postDelayed(mFadeOut, timeout);
        }
        mControllerView.show();
    }

    /**
     * 当前Window是否显示
     */
    public boolean isShowing() {
        return mShowing;
    }

    /**
     * 隐藏控制器
     */
    public void hide() {
        if (mAnchor == null)
            return;
        try {
            mWindowManager.removeView(mDecor);
        } catch (IllegalArgumentException ex) {
            Log.w("MediaController", "already removed");
        }
        mShowing = false;

    }

    /**
     * 取消延时隐藏
     */
    public void cancelFadeOut() {
        removeCallbacks(mFadeOut);
    }

    /**
     * 切换控制层视图、TinyControllerView 与 TinyControllerView
     */
    public void toggleControllerView(ControllerView controllerView) {
        removeAllViews();
        if (controllerView instanceof TinyControllerView) {
            addView(controllerView.getRootView(), mTinyParams);
            mCurrentViewState = TINY_VIEW;
        } else {
            mCurrentViewState = FULL_SCREEN_VIEW;
            ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(controllerView.getRootView(), layoutParams);
        }
        if (mControllerListener != null) {
            if (controllerView instanceof FullScreenControllerView) {
                mControllerListener.onFullScreenClick();
            } else if (controllerView instanceof TinyControllerView) {
                mControllerListener.onTinyScreenClick();
            }
        }
        mControllerView = controllerView;
        mControllerView.show();

    }

    /**
     * 显示网络错误布局
     */
    public void showErrorView() {
        removeAllViews();
        if (mErrorView == null) {
            mErrorView = new ErrorControllerView(mPlayer, this, mCurrentVideoInfo, mContext);
        }
        if (mControllerView instanceof TinyControllerView) {
            addView(mErrorView, mTinyParams);
        } else {
            ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(mErrorView, layoutParams);
        }
        if (!mShowing && mAnchor != null) {
            updateFloatingWindowLayout();
            mWindowManager.addView(mDecor, mDecorLayoutParams);
            mShowing = true;
        }
        mCurrentViewState = ERROR_VIEW;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
                //如果传入的是activity直接退出
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        mCurrentIndex = currentIndex;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    /**
     * 判断是否拥有上一个视频
     */
    public boolean isHavePreVideo() {
        return mTotalCount > 0 && mCurrentIndex > 0;
    }

    /**
     * 是否拥有下一个视频
     */
    public boolean isHaveNextVideo() {
        return mTotalCount > 0 && (mCurrentIndex < mTotalCount - 1);
    }

    /**
     * 重置状态，最小化，与全屏
     */
    public void resetType() {
        if (mControllerView instanceof TinyControllerView) {
            mCurrentViewState = TINY_VIEW;
        } else {
            mCurrentViewState = FULL_SCREEN_VIEW;
        }
    }

    public void updateProgressAndTime(VideoProgressEvent videoProgressEvent){
        mControllerView.updateProgressAndTime(videoProgressEvent);
    }

    /**
     * 控制层监听
     */
    private ControllerListener mControllerListener;

    public ControllerListener getControllerListener() {
        return mControllerListener;
    }

    public void setControllerListener(ControllerListener controllerListener) {
        mControllerListener = controllerListener;
    }


    public interface ControllerListener {

        //退出点击
        void onBackClick();

        //上一页点击
        void onPreClick();

        //下一页点击
        void onNextClick();

        //全屏点击
        void onFullScreenClick();

        //退出全屏
        void onTinyScreenClick();

        //错误界面点击
        void onErrorViewClick();

    }
}
