package com.jennifer.andy.simpleeyes.player

import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.base.utils.getScreenHeight
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.player.view.ControllerViewFactory
import tv.danmaku.ijk.media.player.IMediaPlayer
import kotlin.math.abs


/**
 * Author:  andy.xwt
 * Date:    2020/3/14 13:53
 * Description: IjkVideoViewWrapper 包含了对声音亮度的控制，同时代理了[IjkVideoView]的相关方法
 */

class IjkVideoViewWrapper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), GestureDetector.OnGestureListener {

    private val mIjkVideoView: IjkVideoView

    private val mPlaceImage: SimpleDraweeView
    private val mCenterProgress: ProgressBar
    private val mGestureDetector: GestureDetector
    private val mAudioManager: AudioManager

    private var mLightDialog: Dialog? = null
    private var mLightProgress: ProgressBar? = null
    private var mVolumeDialog: Dialog? = null
    private var mVolumeProgress: ProgressBar? = null

    private var isShowVolume = false
    private var isShowLight = false
    private var isShowPosition = false

    private val mScreenHeight: Int
    private var mParent: ViewGroup? = null


    companion object {
        private const val MIN_SCROLL = 3
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_ijk_wrapper, this, true)
        mGestureDetector = GestureDetector(context, this)
        mAudioManager = getContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mScreenHeight = getContext().getScreenHeight()

        mIjkVideoView = findViewById(R.id.video_view)
        mPlaceImage = findViewById(R.id.iv_place_image)
        mCenterProgress = findViewById(R.id.progress)
    }

    fun setPlaceImageUrl(url: String?) {
        togglePlaceImage(true)
        mPlaceImage.setImageURI(url)
    }

    fun togglePlaceImage(visibility: Boolean) {
        mPlaceImage.visibility = if (visibility) View.VISIBLE else View.GONE
        mCenterProgress.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    fun hidePlaceImage() {
        mPlaceImage.handler.postDelayed({ togglePlaceImage(false) }, 500)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val relValue = mGestureDetector.onTouchEvent(event)
        val action = event.action
        if (action == MotionEvent.ACTION_UP) { //当手指抬起的时候
            onUp()
        } else if (action == MotionEvent.ACTION_CANCEL) {
            onCancel()
        }
        return relValue
    }

    override fun onDown(e: MotionEvent?): Boolean {
        // 单击，触摸屏按下时立刻触发
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
        // 短按，触摸屏按下后片刻后抬起，会触发这个手势，如果迅速抬起则不会
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        // 抬起，手指离开触摸屏时触发(长按、滚动、滑动时，不会触发这个手势)
        if (mIjkVideoView.isInPlaybackState() && mIjkVideoView.getMediaController() != null && (!isShowVolume || !isShowLight || isShowPosition)) {
            mIjkVideoView.toggleMediaControlsVisible()
        }
        return true
    }

    // 滚动，触摸屏按下后移动
    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        if (mIjkVideoView.screenState == ControllerViewFactory.FULL_SCREEN_MODE) { //判断是否是全屏
            val downX = e1.x
            val actuallyDy = e2.y - e1.y
            val actuallyDx = e2.x - e1.x
            val absDy = abs(actuallyDy)
            val absDx = abs(actuallyDx)
            //左右移动
            if (absDx >= MIN_SCROLL && absDx > absDy) {
                showMoveToPositionDialog()
            }
            //上下移动
            if (abs(distanceY) >= MIN_SCROLL && absDy > absDx) {
                if (downX <= mScreenHeight * 0.5f) { //左边，改变声音
                    showVolumeDialog(distanceY)
                } else { //右边改变亮度
                    showLightDialog(distanceY)
                }
            }
        }
        return true
    }

    /**
     * 移动到相应位置
     */
    private fun showMoveToPositionDialog() {
        // TODO: 2018/2/26 xwt 移动到相应的位置
        isShowPosition = true
    }

    private fun onCancel() {
        onUp()
    }

    private fun onUp() {
        dismissLightDialog()
        dismissVolumeDialog()
    }

    //手势识别中不需要使用的方法
    override fun onLongPress(e: MotionEvent?) {}

    //手势识别中不需要使用的方法
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    /**
     * 显示声音控制对话框
     */
    private fun showVolumeDialog(deltaY: Float) {
        isShowVolume = true
        //记录滑动时候当前的声音
        var currentVideoVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        if (deltaY < 0) { //向下滑动
            currentVideoVolume--
        } else { //向下滑动
            currentVideoVolume++
        }
        //设置声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVideoVolume, 0)
        if (mVolumeDialog == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_volume_controller, null)
            mVolumeProgress = view.findViewById(R.id.pb_volume_progress)
            mVolumeDialog = createDialogWithView(view, Gravity.START or Gravity.CENTER_VERTICAL)
        }
        if (!mVolumeDialog!!.isShowing) {
            mVolumeDialog!!.show()
        }
        //设置进度条
        val nextVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        var volumePercent = (nextVolume * 1f / maxVolume * 100f).toInt()
        if (volumePercent > 100) {
            volumePercent = 100
        } else if (volumePercent < 0) {
            volumePercent = 0
        }
        mVolumeProgress!!.progress = volumePercent
    }

    /**
     * 隐藏声音对话框
     */
    private fun dismissVolumeDialog() {
        if (mVolumeDialog != null) {
            mVolumeDialog!!.dismiss()
            isShowVolume = false
        }
    }

    /**
     * 显示控制亮度对话框
     */
    private fun showLightDialog(deltaY: Float) {
        isShowLight = true
        var screenBrightness = 0f
        //记录滑动前的亮度
        val lp = getWindow(context)!!.attributes
        if (lp.screenBrightness < 0) {
            try {
                screenBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat()
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
            }
        } else {
            screenBrightness = lp.screenBrightness
        }
        if (deltaY < 0) { //向下滑动
            screenBrightness -= 0.03f
        } else { //向下滑动
            screenBrightness += 0.03f
        }
        when {
            screenBrightness >= 1 -> {
                lp.screenBrightness = 1f
            }
            screenBrightness < 0 -> {
                lp.screenBrightness = 0.1f
            }
            else -> {
                lp.screenBrightness = screenBrightness
            }
        }
        getWindow(context)!!.attributes = lp
        //设置亮度百分比
        if (mLightProgress == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_light_controller, null)
            mLightProgress = view.findViewById(R.id.pb_light_progress)
            mLightDialog = createDialogWithView(view, Gravity.END or Gravity.CENTER_VERTICAL)
        }
        if (!mLightDialog!!.isShowing) {
            mLightDialog?.show()
        }
        val lightPercent = (screenBrightness * 100f).toInt()
        mLightProgress?.progress = lightPercent
    }

    private fun dismissLightDialog() {
        mLightDialog?.let {
            mLightDialog!!.dismiss()
            isShowLight = false
        }
    }

    /**
     * 根据View与位置创建dialog
     *
     * @param localView 内容布局
     * @param gravity   位置
     */
    fun createDialogWithView(localView: View?, gravity: Int): Dialog {
        return Dialog(context, R.style.VideoProgress).apply {
            setContentView(localView)
            window.apply {
                addFlags(Window.FEATURE_ACTION_BAR)
                addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                setLayout(-2, -2)
                setBackgroundDrawable(ColorDrawable())
                val localLayoutParams = attributes
                localLayoutParams.gravity = gravity
                attributes = localLayoutParams
            }
        }
    }


    /**
     * 进入全屏
     */
    fun enterFullScreen() {
        hideActionBar(context)
        getActivity(context)!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val contentView = getActivity(context)!!.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        mParent = parent as ViewGroup
        mParent!!.removeView(this)
        mIjkVideoView.screenState = ControllerViewFactory.FULL_SCREEN_MODE
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.addView(this, params)
    }

    /**
     * 退出全屏
     */
    fun exitFullScreen() {
        showActionBar(context)
        val contentView = getActivity(context)!!.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        getActivity(context)!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        contentView.removeView(this)
        mIjkVideoView.screenState = ControllerViewFactory.TINY_MODE
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        mParent!!.addView(this, params)
    }


    ///////////////////////////////////////////////////////////////////////////
    // 代理IjkVideoView方法
    ///////////////////////////////////////////////////////////////////////////

    fun setVideoPath(playUrl: String) {
        mIjkVideoView.setVideoPath(playUrl)
    }

    fun start() {
        mIjkVideoView.start()
    }

    fun setMediaController(controller: IjkMediaController?) {
        mIjkVideoView.setMediaController(controller)
    }

    fun toggleAspectRatio(currentAspectRatio: Int) {
        mIjkVideoView.toggleAspectRatio(currentAspectRatio)
    }

    fun setOnPreparedListener(onPreparedListener: IMediaPlayer.OnPreparedListener) {
        mIjkVideoView.setOnPreparedListener(onPreparedListener)
    }

    fun setOnErrorListener(onErrorListener: IMediaPlayer.OnErrorListener) {
        mIjkVideoView.setOnErrorListener(onErrorListener)
    }

    fun setOnCompletionListener(onCompletionListener: IMediaPlayer.OnCompletionListener) {
        mIjkVideoView.setOnCompletionListener(onCompletionListener)
    }

    fun stopPlayback() {
        mIjkVideoView.stopPlayback()
    }

    fun isPlaying(): Boolean {
        return mIjkVideoView.isPlaying
    }

    fun pause() {
        mIjkVideoView.pause()
    }

    fun release(clearTargetState: Boolean) {
        mIjkVideoView.release(clearTargetState)
    }

    fun showErrorView(): Boolean {
        mCenterProgress.handler.postDelayed({
            mCenterProgress.visibility = View.GONE
            mIjkVideoView.getMediaController()!!.showErrorView()
        }, 1000)
        return false
    }

    fun resetType() {
        mIjkVideoView.getMediaController()?.resetType()
    }

    fun setDragging(dragging: Boolean) {
        mIjkVideoView.getMediaController()!!.controllerView!!.isDragging = dragging
    }

    fun isDragging(): Boolean {
        return mIjkVideoView.getMediaController()!!.controllerView!!.isDragging
    }

    fun showControllerAllTheTime() {
        mIjkVideoView.getMediaController()?.showAllTheTime()
    }

    fun getDuration(): Int {
        return mIjkVideoView.duration
    }

    fun showController() {
        mIjkVideoView.getMediaController()!!.show()
    }

    fun seekTo(msec: Int) {
        mIjkVideoView.seekTo(msec)
    }

}
