package com.jennifer.andy.simpleeyes.player.render

import android.annotation.TargetApi
import android.content.Context
import android.graphics.SurfaceTexture
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.jennifer.andy.simpleeyes.player.MeasureHelper
import com.jennifer.andy.simpleeyes.player.render.IRenderView.IRenderCallback
import com.jennifer.andy.simpleeyes.player.render.IRenderView.ISurfaceHolder
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.ISurfaceTextureHolder
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 22:47
 * Description:surfaceView渲染界面
 */

class SurfaceRenderView : SurfaceView, IRenderView {

    private lateinit var mMeasureHelper: MeasureHelper

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context)
    }

    private fun initView(context: Context) {
        mMeasureHelper = MeasureHelper(this)
        mSurfaceCallback = SurfaceCallback(this)
        holder.addCallback(mSurfaceCallback)
        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL)
    }

    override fun getView() = this

    override fun shouldWaitForResize() = true


    //--------------------
    // Layout & Measure
    //--------------------
    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper.setVideoSize(videoWidth, videoHeight)
            holder.setFixedSize(videoWidth, videoHeight)
            requestLayout()
        }
    }

    override fun setVideoSampleAspectRatio(videoSarNum: Int, videoSarDen: Int) {
        if (videoSarNum > 0 && videoSarDen > 0) {
            mMeasureHelper.setVideoSampleAspectRatio(videoSarNum, videoSarDen)
            requestLayout()
        }
    }

    override fun setVideoRotation(degree: Int) {
        Log.e("", "SurfaceView doesn't support rotation ($degree)!\n")
    }

    override fun setAspectRatio(aspectRatio: Int) {
        mMeasureHelper.setAspectRatio(aspectRatio)
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mMeasureHelper.measuredWidth, mMeasureHelper.measuredHeight)
    }

    //--------------------
    // SurfaceViewHolder
    //--------------------
    private class InternalSurfaceHolder(private val surfaceView: SurfaceRenderView,
                                        override val surfaceHolder: SurfaceHolder?) : ISurfaceHolder {

        override fun bindToMediaPlayer(mp: IMediaPlayer?) {
            if (mp != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                        mp is ISurfaceTextureHolder) {
                    val textureHolder = mp as ISurfaceTextureHolder
                    textureHolder.surfaceTexture = null
                }
                mp.setDisplay(surfaceHolder)
            }
        }

        override val renderView: IRenderView
            get() = surfaceView

        override val surfaceTexture: SurfaceTexture?
            get() = null

        override fun openSurface(): Surface? {
            return surfaceHolder?.surface
        }

    }

    //-------------------------
    // SurfaceHolder.Callback
    //-------------------------
    override fun addRenderCallback(callback: IRenderCallback) {
        mSurfaceCallback.addRenderCallback(callback)
    }

    override fun removeRenderCallback(callback: IRenderCallback) {
        mSurfaceCallback.removeRenderCallback(callback)
    }

    private lateinit var mSurfaceCallback: SurfaceCallback

    private class SurfaceCallback(surfaceView: SurfaceRenderView) : SurfaceHolder.Callback {
        private var mSurfaceHolder: SurfaceHolder? = null
        private var mIsFormatChanged = false
        private var mFormat = 0
        private var mWidth = 0
        private var mHeight = 0
        private val mWeakSurfaceView: WeakReference<SurfaceRenderView> = WeakReference(surfaceView)
        private val mRenderCallbackMap: MutableMap<IRenderCallback, Any> = ConcurrentHashMap()

        fun addRenderCallback(callback: IRenderCallback) {
            mRenderCallbackMap[callback] = callback
            var surfaceHolder: ISurfaceHolder? = null
            if (mSurfaceHolder != null) {
                if (surfaceHolder == null) surfaceHolder = InternalSurfaceHolder(mWeakSurfaceView.get()!!, mSurfaceHolder)
                callback.onSurfaceCreated(surfaceHolder, mWidth, mHeight)
            }
            if (mIsFormatChanged) {
                if (surfaceHolder == null) surfaceHolder = InternalSurfaceHolder(mWeakSurfaceView.get()!!, mSurfaceHolder)
                callback.onSurfaceChanged(surfaceHolder, mFormat, mWidth, mHeight)
            }
        }

        fun removeRenderCallback(callback: IRenderCallback) {
            mRenderCallbackMap.remove(callback)
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            mSurfaceHolder = holder
            mIsFormatChanged = false
            mFormat = 0
            mWidth = 0
            mHeight = 0
            //调用监听
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakSurfaceView.get()!!, mSurfaceHolder)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceCreated(surfaceHolder, 0, 0)
            }
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            mSurfaceHolder = null
            mIsFormatChanged = false
            mFormat = 0
            mWidth = 0
            mHeight = 0
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakSurfaceView.get()!!, mSurfaceHolder)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceDestroyed(surfaceHolder)
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                    width: Int, height: Int) {
            mSurfaceHolder = holder
            mIsFormatChanged = true
            mFormat = format
            mWidth = width
            mHeight = height
            // mMeasureHelper.setVideoSize(width, height);
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakSurfaceView.get()!!, mSurfaceHolder)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceChanged(surfaceHolder, format, width, height)
            }
        }

    }

    //--------------------
    // Accessibility
    //--------------------
    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = SurfaceRenderView::class.java.name
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            info.className = SurfaceRenderView::class.java.name
        }
    }
}