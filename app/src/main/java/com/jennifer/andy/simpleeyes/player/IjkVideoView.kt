package com.jennifer.andy.simpleeyes.player

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController.MediaPlayerControl
import com.jennifer.andy.base.rx.RxBus.post
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.player.render.IRenderView
import com.jennifer.andy.simpleeyes.player.render.IRenderView.IRenderCallback
import com.jennifer.andy.simpleeyes.player.render.IRenderView.ISurfaceHolder
import com.jennifer.andy.simpleeyes.player.render.SurfaceRenderView
import com.jennifer.andy.simpleeyes.player.render.TextureRenderView
import com.jennifer.andy.simpleeyes.player.view.ControllerViewFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.misc.IMediaDataSource
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Author:  andy.xwt
 * Date:    2020/3/12 23:03
 * Description:
 */

class IjkVideoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), MediaPlayerControl {

    private var mUri: Uri? = null
    private var mHeaders: Map<String, String>? = null

    private var mSurfaceHolder: ISurfaceHolder? = null
    private var mMediaPlayer: IMediaPlayer? = null
    private var mVideoWidth = 0
    private var mVideoHeight = 0
    private var mSurfaceWidth = 0
    private var mSurfaceHeight = 0

    private var mRenderView: IRenderView? = null
    private var mCurrentRender = RENDER_NONE
    private var mRenderUIView: View? = null

    private var mVideoRotationDegree = 0
    private var mVideoSarNum = 0
    private var mVideoSarDen = 0
    private var mCurrentAspectRatio = allAspectRatio[0]
    private var mMediaController: IjkMediaController? = null


    private var mOnCompletionListener: IMediaPlayer.OnCompletionListener? = null
    private var mOnPreparedListener: IMediaPlayer.OnPreparedListener? = null
    private var mOnErrorListener: IMediaPlayer.OnErrorListener? = null
    private var mOnInfoListener: IMediaPlayer.OnInfoListener? = null

    private var mCurrentBufferPercentage = 0
    private var mSeekWhenPrepared = 0

    private val mCanPause = true
    private val mCanSeekBack = true
    private val mCanSeekForward = true

    private lateinit var mAppContext: Context


    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE
    var screenState = ControllerViewFactory.TINY_MODE


    private val mSHCallback: IRenderCallback = object : IRenderCallback {
        override fun onSurfaceChanged(holder: ISurfaceHolder, format: Int, w: Int, h: Int) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceChanged: unmatched render callback\n")
                return
            }
            mSurfaceWidth = w
            mSurfaceHeight = h
            val isValidState = mTargetState == STATE_PLAYING
            val hasValidSize = !mRenderView!!.shouldWaitForResize() || mVideoWidth == w && mVideoHeight == h
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0) {
                    seekTo(mSeekWhenPrepared)
                }
                start()
            }
        }

        override fun onSurfaceCreated(holder: ISurfaceHolder, width: Int, height: Int) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceCreated: unmatched render callback\n")
                return
            }
            mSurfaceHolder = holder
            if (mMediaPlayer != null) bindSurfaceHolder(mMediaPlayer, holder) else openVideo()
        }

        override fun onSurfaceDestroyed(holder: ISurfaceHolder) {
            if (holder.renderView !== mRenderView) {
                Log.e(TAG, "onSurfaceDestroyed: unmatched render callback\n")
                return
            }
            // after we return from this we can't use the surface any more
            mSurfaceHolder = null
            // REMOVED: if (mMediaController != null) mMediaController.hide();
            // REMOVED: release(true);
            releaseWithoutStop()
        }
    }

    companion object {

        private const val STATE_ERROR = -1
        private const val STATE_IDLE = 0
        private const val STATE_PREPARING = 1
        private const val STATE_PREPARED = 2
        private const val STATE_PLAYING = 3
        private const val STATE_PAUSED = 4
        private const val STATE_PLAYBACK_COMPLETED = 5

        private val allAspectRatio = intArrayOf(
                IRenderView.AR_ASPECT_FIT_PARENT,
                IRenderView.AR_ASPECT_FILL_PARENT,
                IRenderView.AR_ASPECT_WRAP_CONTENT,
                IRenderView.AR_16_9_FIT_PARENT,
                IRenderView.AR_4_3_FIT_PARENT)
        const val RENDER_NONE = 0
        const val RENDER_SURFACE_VIEW = 1
        const val RENDER_TEXTURE_VIEW = 2
        private const val TAG = "IjkVideoView"
    }

    init {
        initVideoView(context)
    }

    fun toggleAspectRatio(currentAspectRatio: Int): Int {
        mRenderView?.setAspectRatio(currentAspectRatio)
        mCurrentAspectRatio = currentAspectRatio
        return mCurrentAspectRatio
    }

    private fun initVideoView(context: Context) {
        mAppContext = context.applicationContext
        initRenders()
        //初始化宽高
        mVideoWidth = 0
        mVideoHeight = 0
        //获取焦点
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        //初始化状态
        mCurrentState = STATE_IDLE
        mTargetState = STATE_IDLE
        //设置背景
        background = ColorDrawable(Color.BLACK)
    }

    /**
     * 设置渲染Render
     * 4.0 及以上版本使用 TextureView
     * 4.0 版本以下使用 SurfaceView
     */
    private fun initRenders() {
        mCurrentRender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) { //4.0以上
            RENDER_TEXTURE_VIEW
        } else {
            RENDER_SURFACE_VIEW
        }
        setRender(mCurrentRender)
    }

    /**
     * 设置渲染Render
     */
    private fun setRender(render: Int) {
        when (render) {
            RENDER_NONE -> setRenderView(null)
            RENDER_TEXTURE_VIEW -> {
                //TextureView
                val renderView = TextureRenderView(context)
                if (mMediaPlayer != null) {
                    renderView.getSurfaceHolder().bindToMediaPlayer(mMediaPlayer)
                    renderView.setVideoSize(mMediaPlayer!!.videoWidth, mMediaPlayer!!.videoHeight)
                    renderView.setVideoSampleAspectRatio(mMediaPlayer!!.videoSarNum, mMediaPlayer!!.videoSarDen)
                    renderView.setAspectRatio(mCurrentAspectRatio)
                }
                setRenderView(renderView)
            }
            RENDER_SURFACE_VIEW -> {
                //surfaceView
                val renderView = SurfaceRenderView(context)
                setRenderView(renderView)
            }
            else -> Log.e(TAG, String.format(Locale.getDefault(), "invalid render %d\n", render))
        }
    }


    /**
     * 设置渲染界面，初始化当前的渲染界面参数，并设置渲染回调等初始化操作
     */
    private fun setRenderView(renderView: IRenderView?) {
        if (mRenderView != null) {
            if (mMediaPlayer != null)
                mMediaPlayer?.setDisplay(null)
            val renderUIView = mRenderView?.getView()
            mRenderView?.removeRenderCallback(mSHCallback)
            mRenderView = null
            removeView(renderUIView)
        }
        if (renderView == null) return
        mRenderView = renderView
        renderView.setAspectRatio(mCurrentAspectRatio) //设置适配比例
        if (mVideoWidth > 0 && mVideoHeight > 0)
            renderView.setVideoSize(mVideoWidth, mVideoHeight) //设置视频的宽高
        if (mVideoSarNum > 0 && mVideoSarDen > 0) //设置采样比例
            renderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)

        mRenderUIView = mRenderView?.getView().apply {
            layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER)
        }
        addView(mRenderUIView)
        mRenderView?.addRenderCallback(mSHCallback) //添加回调
        mRenderView?.setVideoRotation(mVideoRotationDegree) //设置视频旋转
    }

    /**
     * 设置视频播放路径
     *
     * @param path 视频路径
     */
    fun setVideoPath(path: String?) {
        setVideoURI(Uri.parse(path))
    }

    /**
     * 设置视频URI
     *
     * @param uri 视频URI
     */
    fun setVideoURI(uri: Uri) {
        setVideoURI(uri, null)
    }

    /**
     * 设置带header 的 视频URI
     *
     *
     * 注意，默认情况下允许跨域重定向，
     * 但可以通过以“android allow cross domain redirect”为键,
     * 以“0”或“1”为值的headers，以禁止或允许跨域重定向。
     *
     * @param uri     视频URI.
     * @param headers URI请求需要的header
     */
    private fun setVideoURI(uri: Uri, headers: Map<String, String>?) {
        mUri = uri
        mHeaders = headers
        mSeekWhenPrepared = 0
        openVideo()
        requestLayout()
        invalidate()
    }

    fun stopPlayback() {
        mMediaPlayer?.let {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
            mCurrentState = STATE_IDLE
            mTargetState = STATE_IDLE

            mMediaController?.let {
                mMediaController?.hide()
                mMediaController = null
            }

            val am = mAppContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.abandonAudioFocus(null)
            cancelProgressRunnable()
        }
    }

    /**
     * 打开视频
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun openVideo() {
        if (mUri == null || mSurfaceHolder == null) { // not ready for playback just yet, will try again later
            return
        }
        // we shouldn't clear the target state, because somebody might have
        // called start() previously
        release(false)
        val am = mAppContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        try {
            mMediaPlayer = createPlayer()
            mMediaPlayer?.setOnPreparedListener(mPreparedListener)
            mMediaPlayer?.setOnVideoSizeChangedListener(mSizeChangedListener)
            mMediaPlayer?.setOnCompletionListener(mCompletionListener)
            mMediaPlayer?.setOnErrorListener(mErrorListener)
            mMediaPlayer?.setOnInfoListener(mInfoListener)
            mMediaPlayer?.setOnBufferingUpdateListener(mBufferingUpdateListener)
            mMediaPlayer?.setOnSeekCompleteListener(mSeekCompleteListener)
            mCurrentBufferPercentage = 0
            val scheme = mUri?.scheme
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (TextUtils.isEmpty(scheme)
                            || scheme.equals("file", ignoreCase = true))) {
                val dataSource: IMediaDataSource = FileMediaDataSource(File(mUri.toString()))
                mMediaPlayer?.setDataSource(dataSource)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mMediaPlayer?.setDataSource(mAppContext, mUri, mHeaders)
            } else {
                mMediaPlayer?.dataSource = mUri.toString()
            }
            bindSurfaceHolder(mMediaPlayer, mSurfaceHolder)
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer?.setScreenOnWhilePlaying(true)
            mMediaPlayer?.prepareAsync()
            mCurrentState = STATE_PREPARING
            attachMediaController()
        } catch (ex: IOException) {
            Log.w(TAG, "Unable to open content: $mUri", ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        } catch (ex: IllegalArgumentException) {
            Log.w(TAG, "Unable to open content: $mUri", ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        } finally { // REMOVED: mPendingSubtitleTracks.clear();
        }
    }

    fun setMediaController(controller: IjkMediaController?) {
        mMediaController = controller
        attachMediaController()
    }

    fun getMediaController(): IjkMediaController? {
        return mMediaController
    }

    private fun attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController?.setMediaPlayer(this)
            val anchorView = if (this.parent is View) this.parent as View else this
            mMediaController?.setAnchorView(anchorView)
            mMediaController?.isEnabled = isInPlaybackState()
        }
    }

    var mSizeChangedListener = IMediaPlayer.OnVideoSizeChangedListener { mp, _, _, _, _ ->
        mVideoWidth = mp.videoWidth
        mVideoHeight = mp.videoHeight
        mVideoSarNum = mp.videoSarNum
        mVideoSarDen = mp.videoSarDen
        if (mVideoWidth != 0 && mVideoHeight != 0) {
            mRenderView?.let {
                mRenderView?.setVideoSize(mVideoWidth, mVideoHeight)
                mRenderView?.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)
            }
            // REMOVED: getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            requestLayout()
        }
    }
    /**
     * 视频播放准备完成监听
     */
    var mPreparedListener = IMediaPlayer.OnPreparedListener { mp ->
        mCurrentState = STATE_PREPARED
        if (mOnPreparedListener != null) {
            mOnPreparedListener!!.onPrepared(mMediaPlayer)
        }
        if (mMediaController != null) {
            mMediaController!!.isEnabled = true
        }
        mVideoWidth = mp.videoWidth
        mVideoHeight = mp.videoHeight
        val seekToPosition = mSeekWhenPrepared // mSeekWhenPrepared may be changed after seekTo() call
        if (seekToPosition != 0) {
            seekTo(seekToPosition)
        }
        if (mVideoWidth != 0 && mVideoHeight != 0) {
            mRenderView?.let {
                mRenderView?.setVideoSize(mVideoWidth, mVideoHeight)
                mRenderView?.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen)
                if (!mRenderView!!.shouldWaitForResize() || mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                    // We didn't actually change the size (it was already at the size
                    // we need), so we won't get a "surface changed" callback, so
                    // start the video here instead of in the callback.
                    if (mTargetState == STATE_PLAYING) {
                        start()
                        mMediaController?.firstShow()
                    } else if (!isPlaying && (seekToPosition != 0 || currentPosition > 0)) {
                        // Show the media controls when we're paused into a video and make 'em stick.
                        mMediaController?.show(0)
                    }
                }
            }
        } else { // We don't know the video size yet, but should start anyway.
            // The video size might be reported to us later.
            if (mTargetState == STATE_PLAYING) {
                start()
            }
        }
    }
    private val mCompletionListener = IMediaPlayer.OnCompletionListener {
        mCurrentState = STATE_PLAYBACK_COMPLETED
        mTargetState = STATE_PLAYBACK_COMPLETED
        mMediaController?.hide()
        mOnCompletionListener?.onCompletion(mMediaPlayer)
    }
    private val mInfoListener = IMediaPlayer.OnInfoListener { mp, arg1, arg2 ->
        mOnInfoListener?.onInfo(mp, arg1, arg2)
        when (arg1) {
            IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING -> Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:")
            IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:")
            IMediaPlayer.MEDIA_INFO_BUFFERING_START -> Log.d(TAG, "MEDIA_INFO_BUFFERING_START:")
            IMediaPlayer.MEDIA_INFO_BUFFERING_END -> Log.d(TAG, "MEDIA_INFO_BUFFERING_END:")
            IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH -> Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: $arg2")
            IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING -> Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:")
            IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE -> Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:")
            IMediaPlayer.MEDIA_INFO_METADATA_UPDATE -> Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:")
            IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE -> Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:")
            IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT -> Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:")
            IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED -> {
                mVideoRotationDegree = arg2
                Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: $arg2")
                if (mRenderView != null) mRenderView!!.setVideoRotation(arg2)
            }
            IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START -> Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:")
        }
        true
    }
    private val mErrorListener = IMediaPlayer.OnErrorListener { mp, framework_err, impl_err ->
        Log.d(TAG, "Error: $framework_err,$impl_err")
        mCurrentState = STATE_ERROR
        mTargetState = STATE_ERROR
        /* If an error handler has been supplied, use it and finish. */if (mOnErrorListener != null) {
        if (mOnErrorListener!!.onError(mMediaPlayer, framework_err, impl_err)) {
            return@OnErrorListener true
        }
    }
        true
    }
    private val mBufferingUpdateListener = IMediaPlayer.OnBufferingUpdateListener { _, percent ->
        mCurrentBufferPercentage = percent
    }
    private val mSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener { }

    /**
     * Register a callback to be invoked when the media file
     * is loaded and ready to go.
     *
     * @param l The callback that will be run
     */
    fun setOnPreparedListener(l: IMediaPlayer.OnPreparedListener?) {
        mOnPreparedListener = l
    }

    /**
     * Register a callback to be invoked when the end of a media file
     * has been reached during playback.
     *
     * @param l The callback that will be run
     */
    fun setOnCompletionListener(l: IMediaPlayer.OnCompletionListener?) {
        mOnCompletionListener = l
    }

    /**
     * Register a callback to be invoked when an error occurs
     * during playback or setup.  If no listener is specified,
     * or if the listener returned false, VideoView will inform
     * the user of any errors.
     *
     * @param l The callback that will be run
     */
    fun setOnErrorListener(l: IMediaPlayer.OnErrorListener?) {
        mOnErrorListener = l
    }

    /**
     * Register a callback to be invoked when an informational event
     * occurs during playback or setup.
     *
     * @param l The callback that will be run
     */
    fun setOnInfoListener(l: IMediaPlayer.OnInfoListener?) {
        mOnInfoListener = l
    }

    // REMOVED: mSHCallback
    private fun bindSurfaceHolder(mp: IMediaPlayer?, holder: ISurfaceHolder?) {
        if (mp == null) return
        if (holder == null) {
            mp.setDisplay(null)
            return
        }
        holder.bindToMediaPlayer(mp)
    }

    fun releaseWithoutStop() {
        if (mMediaPlayer != null) mMediaPlayer!!.setDisplay(null)
    }

    /*
     * release the media player in any state
     */
    fun release(clearTargetState: Boolean) {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
            mMediaPlayer!!.release()
            mMediaPlayer = null
            mMediaController!!.hide()
            if (clearTargetState) {
                mTargetState = STATE_IDLE
            }
            setRenderView(null)
            initRenders()
            val am = mAppContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            am.abandonAudioFocus(null)
            cancelProgressRunnable()
        }
    }

    override fun onTrackballEvent(ev: MotionEvent?): Boolean {
        if (isInPlaybackState() && mMediaController != null) {
            toggleMediaControlsVisible()
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN && keyCode != KeyEvent.KEYCODE_VOLUME_MUTE && keyCode != KeyEvent.KEYCODE_MENU && keyCode != KeyEvent.KEYCODE_CALL && keyCode != KeyEvent.KEYCODE_ENDCALL
        if (isInPlaybackState() && isKeyCodeSupported && mMediaController != null) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK ||
                    keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (mMediaPlayer!!.isPlaying) {
                    pause()
                    mMediaController!!.show()
                } else {
                    start()
                    mMediaController!!.hide()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
                if (!mMediaPlayer!!.isPlaying) {
                    start()
                    mMediaController!!.hide()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
                if (mMediaPlayer!!.isPlaying) {
                    pause()
                    mMediaController!!.show()
                }
                return true
            } else {
                toggleMediaControlsVisible()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun toggleMediaControlsVisible() {

        if (mMediaController!!.isShowing) {
            mMediaController?.hide()
        } else {
            mMediaController?.show()
        }
    }

    private var mDisposable: Disposable? = null


    /**
     * 进度与时间更新线程
     */
    private fun startProgressRunnable() {
        if (mDisposable == null || mDisposable!!.isDisposed) {
            mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val position = currentPosition
                        val duration = duration
                        if (duration >= 0 && bufferPercentage > 0) {
                            val progress = 1000L * position / duration
                            val secondProgress = bufferPercentage * 10
                            //发送进度
                            val event = VideoProgressEvent(duration, position, progress.toInt(), secondProgress)
                            //这里在更新进度条时，有可能在切换控制器，所以这里要排除空的情况
                            if (mMediaController != null) {
                                mMediaController!!.updateProgressAndTime(event)
                            }
                            post(event)
                        }
                    }
        }
    }

    /**
     * 取消进度与时间更新线程
     */
    private fun cancelProgressRunnable() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable?.dispose()
            mDisposable = null
        }
    }

    override fun start() {
        if (isInPlaybackState()) {
            mMediaPlayer?.start()
            mCurrentState = STATE_PLAYING
            startProgressRunnable()
        }
        mTargetState = STATE_PLAYING
    }

    override fun pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer!!.isPlaying) {
                mMediaPlayer?.pause()
                mCurrentState = STATE_PAUSED
                cancelProgressRunnable()
            }
        }
        mTargetState = STATE_PAUSED
    }

    fun suspend() {
        release(false)
        cancelProgressRunnable()
    }

    fun resume() {
        openVideo()
    }

    override fun getDuration(): Int {
        return if (isInPlaybackState()) {
            mMediaPlayer?.duration!!.toInt()
        } else -1
    }

    override fun getCurrentPosition(): Int {
        return if (isInPlaybackState()) {
            mMediaPlayer?.currentPosition!!.toInt()
        } else 0
    }

    override fun seekTo(msec: Int) {
        mSeekWhenPrepared = if (isInPlaybackState()) {
            mMediaPlayer?.seekTo(msec.toLong())
            0
        } else {
            msec
        }
    }

    override fun isPlaying(): Boolean {
        return isInPlaybackState() && mMediaPlayer!!.isPlaying
    }

    override fun getBufferPercentage(): Int {
        return if (mMediaPlayer != null) {
            mCurrentBufferPercentage
        } else 0
    }

    fun isInPlaybackState(): Boolean {
        return mMediaPlayer != null
                && mCurrentState != STATE_ERROR
                && mCurrentState != STATE_IDLE
                && mCurrentState != STATE_PREPARING
    }

    override fun canPause() = mCanPause


    override fun canSeekBackward() = mCanSeekBack

    override fun canSeekForward() = mCanSeekForward

    override fun getAudioSessionId(): Int {
        return 0
    }

    private fun createPlayer(): IMediaPlayer? {
        if (mUri != null) {
            return IjkMediaPlayer().apply {
                IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG)
                setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0)
                setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0)
                setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32.toLong())
                setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1)
                setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0)
                setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0)
                setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48)
            }
        }
        return null
    }

}
