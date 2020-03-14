package com.jennifer.andy.simpleeyes.player

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.media.AudioManager
import android.util.Log
import android.view.*
import android.view.View.OnLayoutChangeListener
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import android.widget.MediaController.MediaPlayerControl
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import com.jennifer.andy.simpleeyes.player.event.VideoProgressEvent
import com.jennifer.andy.simpleeyes.player.view.ControllerView
import com.jennifer.andy.simpleeyes.player.view.ControllerViewFactory


/**
 * Author:  andy.xwt
 * Date:    2020/3/10 17:38
 * Description:IjkMediaController 用于视频播放的UI，内部原理是通过window进行展示，其中window的位置与大小
 * 是根据[mAnchor]的位置动态调整的。
 *
 */

class IjkMediaController(context: Context) : FrameLayout(context) {

    private lateinit var player: MediaPlayerControl

    private lateinit var windowManager: WindowManager
    private lateinit var window: Window
    private lateinit var decorLayoutParams: WindowManager.LayoutParams
    private lateinit var decorView: View

    private lateinit var mAnchor: View//锚点view，用于更新window的位置

    var isShowing = false//当前window是否展示

    var currentVideoInfo: ContentBean? = null
    var totalCount = 0
    var currentIndex = 0

    private var showMode1 = ControllerViewFactory.TINY_MODE //当前window展示的类型，默认情况下是小视图

    var controllerListener: ControllerListener? = null
    var controllerView: ControllerView? = null

    private val mControllerViewFactory = ControllerViewFactory()

    companion object {
        private const val WINDOW_TIME_OUT = 3500L //默认window消失时间 3.5秒
    }

    init {
        initFloatingWindowLayout()
        initFloatingWindowConfig()
    }

    /**
     * 初始化window布局
     */
    private fun initFloatingWindowLayout() {
        decorLayoutParams = WindowManager.LayoutParams().apply {
            gravity = Gravity.TOP or Gravity.LEFT
            height = LayoutParams.WRAP_CONTENT
            x = 0
            format = PixelFormat.TRANSLUCENT
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            flags = flags or (WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_SPLIT_TOUCH)
            token = null
            windowAnimations = 0
        }

    }

    /**
     * 初始化window参数
     */
    private fun initFloatingWindowConfig() {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        window = PolicyCompat().createPhoneWindow(context).apply {
            setWindowManager(windowManager, null, null)
            requestFeature(Window.FEATURE_NO_TITLE)
            setContentView(this@IjkMediaController)
            setBackgroundDrawableResource(R.color.transparent)
            volumeControlStream = AudioManager.STREAM_MUSIC
        }

        decorView = window.decorView
        decorView.setOnTouchListener(mTouchListener)
        isFocusable = true
        isFocusableInTouchMode = true
        descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
        requestFocus()
    }


    /**
     * 初始化数据
     *
     * @param currentIndex     当前视频角标
     * @param totalCount       视频集合总数
     * @param currentVideoInfo 当前视频信息
     */
    fun initData(currentIndex: Int, totalCount: Int, currentVideoInfo: ContentBean?) {
        this.currentIndex = currentIndex
        this.totalCount = totalCount
        this.currentVideoInfo = currentVideoInfo
    }

    /**
     * 当锚点布局发生改变时，动态更新window的位置，高度与宽度
     */
    private fun updateFloatingWindowLayout() {
        val anchorPos = IntArray(2)
        mAnchor.getLocationOnScreen(anchorPos)

        //重新测量
        decorView.measure(MeasureSpec.makeMeasureSpec(mAnchor.width, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(mAnchor.height, MeasureSpec.AT_MOST))

        decorLayoutParams.apply {
            width = mAnchor.width
            x = anchorPos[0]
            y = anchorPos[1]
            height = mAnchor.height
        }

    }

    fun setMediaPlayer(player: MediaPlayerControl) {
        this.player = player
        switchControllerView(showMode1)
    }

    /**
     * 当锚点view布局发生改变的时,重新设置当前window的布局参数
     */
    private val mLayoutChangeListener = OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        updateFloatingWindowLayout()
        if (isShowing) windowManager.updateViewLayout(decorView, decorLayoutParams)

    }

    /**
     * 将控制层view与锚点的关联
     */
    fun setAnchorView(view: View) {
        mAnchor = view
        mAnchor.addOnLayoutChangeListener(mLayoutChangeListener)
        updateFloatingWindowLayout()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Window显示相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 触摸监听，如果当前window正在显示，再次按下走默认时间内该window会消失
     */
    private val mTouchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (isShowing) {
                removeCallbacks(mFadeOut)
                postDelayed(mFadeOut, WINDOW_TIME_OUT)
            }
        }
        false
    }

    /**
     * 隐藏控制层view
     */
    private val mFadeOut = Runnable {
        if (isShowing) hide()
    }

    /**
     * 第一次显示，不显示控制层
     */
    fun firstShow() {
        if (!isShowing && controllerView != null) {
            controllerView?.display()
        }
    }

    /**
     * 将控制器显示在屏幕上，当到达过期时间时会自动消失。
     *
     * @param timeout 过期时间(毫秒) 如果设置为0，那么会直到调用hide方法才会消失
     */
    @JvmOverloads
    fun show(timeout: Long = WINDOW_TIME_OUT) {
        if (!isShowing) {
            windowManager.addView(decorView, decorLayoutParams)
            isShowing = true
        }
        if (timeout != 0L) {
            removeCallbacks(mFadeOut)
            postDelayed(mFadeOut, timeout.toLong())
        }
        controllerView?.display()
        controllerListener?.onShowController(true)

    }

    /**
     * 隐藏window
     */
    fun hide() {
        try {
            removeCallbacks(mFadeOut)
            isShowing = false
            controllerListener?.onShowController(false)
            windowManager.removeView(decorView)
        } catch (ex: IllegalArgumentException) {
            Log.w("MediaController", "already removed")
        }
    }

    /**
     * 一直显示window
     */
    fun showAllTheTime() {
        show(3600000)
        removeCallbacks(mFadeOut)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        val uniqueDown = (event.repeatCount == 0 && event.action == KeyEvent.ACTION_DOWN)
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide()
                //如果传入的是activity直接退出
                if (context is Activity) (context as Activity).finish()

            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Window显示内容相关
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 根据显示模式切换展示的控制UI
     *
     * @param showMode
     */
    fun switchControllerView(showMode: Int) {
        showMode1 = showMode
        removeAllViews()
        controllerView = mControllerViewFactory.create(showMode, context)
        controllerView?.initControllerAndData(player, this, currentVideoInfo)
        controllerView?.display()
        addView(controllerView)
    }

    /**
     * 显示错误布局
     */
    fun showErrorView() {
        if (!isShowing) {
            controllerView?.showErrorView()
            windowManager.addView(decorView, decorLayoutParams)
            isShowing = true
        }
    }

    fun updateProgressAndTime(videoProgressEvent: VideoProgressEvent?) {
        controllerView?.updateProgressAndTime(videoProgressEvent!!)
    }

    fun resetType() {
        controllerView?.showContent()
    }

    ///////////////////////////////////////////////////////////////////////////
    // 数据相关
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 判断是否拥有上一个视频
     */
    fun isHavePreVideo() = totalCount > 0 && currentIndex > 0


    /**
     * 是否拥有下一个视频
     */
    fun isHaveNextVideo() = totalCount > 0 && currentIndex < totalCount - 1


    ///////////////////////////////////////////////////////////////////////////
    // 回调监听
    ///////////////////////////////////////////////////////////////////////////

    interface ControllerListener {
        //退出点击
        fun onBackClick()

        //上一页点击
        fun onPreClick()

        //下一页点击
        fun onNextClick()

        //全屏点击
        fun onFullScreenClick()

        //退出全屏
        fun onTinyScreenClick()

        //错误界面点击
        fun onErrorViewClick()

        //是否显示控制层
        fun onShowController(isShowController: Boolean)
    }


}