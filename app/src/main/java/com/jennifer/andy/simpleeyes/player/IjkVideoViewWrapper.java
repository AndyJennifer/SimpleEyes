package com.jennifer.andy.simpleeyes.player;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jennifer.andy.simpleeyes.R;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static com.jennifer.andy.base.utils.ScreenUtilsKt.getScreenHeight;
import static com.jennifer.andy.simpleeyes.player.IjkVideoView.SCREEN_FULL_SCREEN;
import static com.jennifer.andy.simpleeyes.player.IjkVideoView.SCREEN_TINY;
import static com.jennifer.andy.simpleeyes.utils.VideoPlayerUtilsKt.getActivity;
import static com.jennifer.andy.simpleeyes.utils.VideoPlayerUtilsKt.getWindow;
import static com.jennifer.andy.simpleeyes.utils.VideoPlayerUtilsKt.hideActionBar;
import static com.jennifer.andy.simpleeyes.utils.VideoPlayerUtilsKt.showActionBar;

/**
 * Author:  andy.xwt
 * Date:    2020-01-21 17:19
 * Description:
 */

public class IjkVideoViewWrapper extends FrameLayout implements GestureDetector.OnGestureListener {

    private IjkVideoView mIjkVideoView;
    private SimpleDraweeView mPlaceImage;
    private ProgressBar mCenterProgress;

    private GestureDetector mGestureDetector;
    private AudioManager mAudioManager;

    private Dialog mLightDialog;
    private ProgressBar mLightProgress;
    private Dialog mVolumeDialog;
    private ProgressBar mVolumeProgress;
    private boolean isShowVolume;
    private boolean isShowLight;
    private boolean isShowPosition;

    private int mScreenHeight;


    private static final int MIN_SCROLL = 3;
    private ViewGroup mParent;

    public IjkVideoViewWrapper(@NonNull Context context) {
        this(context, null);
    }

    public IjkVideoViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IjkVideoViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_ijk_wrapper, this, true);
        mGestureDetector = new GestureDetector(context, this);
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mScreenHeight = getScreenHeight(getContext());
        mIjkVideoView = findViewById(R.id.video_view);
        mPlaceImage = findViewById(R.id.iv_place_image);
        mCenterProgress = findViewById(R.id.progress);
    }

    public void setPlaceImageUrl(String url) {
        togglePlaceImage(true);
        mPlaceImage.setImageURI(url);
    }

    public void togglePlaceImage(boolean visibility) {
        mPlaceImage.setVisibility(visibility ? View.VISIBLE : View.GONE);
        mCenterProgress.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void hidePlaceImage() {
        mPlaceImage.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                togglePlaceImage(false);
            }
        }, 500);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean relValue = mGestureDetector.onTouchEvent(event);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            //当手指抬起的时候
            onUp();
        } else if (action == MotionEvent.ACTION_CANCEL) {
            onCancel();
        }
        return relValue;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        // 单击，触摸屏按下时立刻触发
        return true;
    }


    @Override
    public void onShowPress(MotionEvent e) {
        // 短按，触摸屏按下后片刻后抬起，会触发这个手势，如果迅速抬起则不会
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // 抬起，手指离开触摸屏时触发(长按、滚动、滑动时，不会触发这个手势)
        if (mIjkVideoView.isInPlaybackState() && mIjkVideoView.getMediaController() != null && (!isShowVolume || !isShowLight || isShowPosition)) {
            mIjkVideoView.toggleMediaControlsVisible();
        }
        return true;
    }

    // 滚动，触摸屏按下后移动
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mIjkVideoView.getScreenState() == SCREEN_FULL_SCREEN) {//判断是否是全屏
            float downX = e1.getX();

            float actuallyDy = e2.getY() - e1.getY();
            float actuallyDx = e2.getX() - e1.getX();

            float absDy = Math.abs(actuallyDy);
            float absDx = Math.abs(actuallyDx);
            //左右移动
            if (absDx >= MIN_SCROLL && absDx > absDy) {
                showMoveToPositionDialog();
            }
            //上下移动
            if (Math.abs(distanceY) >= MIN_SCROLL && absDy > absDx) {
                if (downX <= (mScreenHeight * 0.5f)) {
                    //左边，改变声音
                    showVolumeDialog(distanceY);
                } else {
                    //右边改变亮度
                    showLightDialog(distanceY);
                }
            }
        }
        return true;
    }


    /**
     * 移动到相应位置
     */
    private void showMoveToPositionDialog() {
        // TODO: 2018/2/26 xwt 移动到相应的位置
        isShowPosition = true;
    }

    private void onCancel() {
        onUp();
    }

    private void onUp() {
        dismissLightDialog();
        dismissVolumeDialog();
    }


    //手势识别中不需要使用的方法
    @Override
    public void onLongPress(MotionEvent e) {
    }

    //手势识别中不需要使用的方法
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    /**
     * 显示声音控制对话框
     */
    private void showVolumeDialog(float deltaY) {

        isShowVolume = true;
        //记录滑动时候当前的声音
        int currentVideoVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (deltaY < 0) {//向下滑动
            currentVideoVolume--;
        } else {//向下滑动
            currentVideoVolume++;
        }
        //设置声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVideoVolume, 0);

        if (mVolumeDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_volume_controller, null);
            mVolumeProgress = view.findViewById(R.id.pb_volume_progress);
            mVolumeDialog = createDialogWithView(view, Gravity.START | Gravity.CENTER_VERTICAL);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }

        //设置进度条
        int nextVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int volumePercent = (int) ((nextVolume * 1f / maxVolume) * 100f);
        if (volumePercent > 100) {
            volumePercent = 100;
        } else if (volumePercent < 0) {
            volumePercent = 0;
        }
        mVolumeProgress.setProgress(volumePercent);

    }

    /**
     * 隐藏声音对话框
     */
    private void dismissVolumeDialog() {
        if (mVolumeDialog != null) {
            mVolumeDialog.dismiss();
            isShowVolume = false;
        }
    }


    /**
     * 显示控制亮度对话框
     */
    private void showLightDialog(float deltaY) {
        isShowLight = true;
        float screenBrightness = 0;
        //记录滑动前的亮度
        WindowManager.LayoutParams lp = getWindow(getContext()).getAttributes();
        if (lp.screenBrightness < 0) {
            try {
                screenBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            screenBrightness = lp.screenBrightness;
        }


        if (deltaY < 0) {//向下滑动
            screenBrightness -= 0.03f;
        } else {//向下滑动
            screenBrightness += 0.03f;
        }
        if (screenBrightness >= 1) {
            lp.screenBrightness = 1f;
        } else if (screenBrightness < 0) {
            lp.screenBrightness = 0.1f;
        } else {
            lp.screenBrightness = screenBrightness;
        }
        getWindow(getContext()).setAttributes(lp);

        //设置亮度百分比
        if (mLightProgress == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_light_controller, null);
            mLightProgress = view.findViewById(R.id.pb_light_progress);
            mLightDialog = createDialogWithView(view, Gravity.END | Gravity.CENTER_VERTICAL);
        }
        if (!mLightDialog.isShowing()) {
            mLightDialog.show();
        }

        int lightPercent = (int) (screenBrightness * 100f);
        mLightProgress.setProgress(lightPercent);
    }

    private void dismissLightDialog() {
        if (mLightDialog != null) {
            mLightDialog.dismiss();
            isShowLight = false;
        }
    }

    /**
     * 根据View与位置创建dialog
     *
     * @param localView 内容布局
     * @param gravity   位置
     */
    public Dialog createDialogWithView(View localView, int gravity) {
        Dialog dialog = new Dialog(getContext(), R.style.VideoProgress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(Window.FEATURE_ACTION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.setLayout(-2, -2);
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.gravity = gravity;
        window.setAttributes(localLayoutParams);
        return dialog;
    }


    /**
     * 进入全屏
     */
    public void enterFullScreen() {
        hideActionBar(getContext());
        getActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup contentView = getActivity(getContext()).findViewById(Window.ID_ANDROID_CONTENT);
        mParent = (ViewGroup) getParent();
        mParent.removeView(this);
        mIjkVideoView.setScreenState(SCREEN_FULL_SCREEN);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        contentView.addView(this, params);
    }

    /**
     * 退出全屏
     */
    public void exitFullScreen() {
        showActionBar(getContext());
        ViewGroup contentView = getActivity(getContext()).findViewById(Window.ID_ANDROID_CONTENT);
        getActivity(getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        contentView.removeView(this);
        mIjkVideoView.setScreenState(SCREEN_TINY);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mParent.addView(this, params);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 代理IjkVideoView方法
    ///////////////////////////////////////////////////////////////////////////

    public void setVideoPath(@NotNull String playUrl) {
        mIjkVideoView.setVideoPath(playUrl);
    }

    public void start() {
        mIjkVideoView.start();
    }


    public void setMediaController(IjkMediaController controller) {
        mIjkVideoView.setMediaController(controller);
    }


    public void toggleAspectRatio(int currentAspectRatio) {
        mIjkVideoView.toggleAspectRatio(currentAspectRatio);
    }

    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        mIjkVideoView.setOnPreparedListener(onPreparedListener);
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        mIjkVideoView.setOnErrorListener(onErrorListener);
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        mIjkVideoView.setOnCompletionListener(onCompletionListener);

    }

    public void stopPlayback() {
        mIjkVideoView.stopPlayback();
    }

    public boolean isPlaying() {
        return mIjkVideoView.isPlaying();
    }

    public void pause() {
        mIjkVideoView.pause();
    }

    public void release(boolean clearTargetState) {
        mIjkVideoView.release(clearTargetState);
    }

    public boolean showErrorView() {
        mCenterProgress.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCenterProgress.setVisibility(View.GONE);
                mIjkVideoView.getMediaController().showErrorView();

            }
        }, 1000);
        return false;
    }

    public void resetType() {
        mIjkVideoView.getMediaController().resetType();
    }

    public void setDragging(boolean dragging) {
        mIjkVideoView.getMediaController().getControllerView().setDragging(dragging);
    }

    public boolean isDragging() {
        return mIjkVideoView.getMediaController().getControllerView().isDragging();
    }

    public void showControllerAllTheTime() {
        IjkMediaController mediaController = mIjkVideoView.getMediaController();
        mediaController.show(3600000);
        mediaController.cancelFadeOut();
    }

    public int getDuration() {
        return mIjkVideoView.getDuration();
    }

    public void showController() {
        mIjkVideoView.getMediaController().show();
    }

    public void seekTo(int msec) {
        mIjkVideoView.seekTo(msec);
    }
}
