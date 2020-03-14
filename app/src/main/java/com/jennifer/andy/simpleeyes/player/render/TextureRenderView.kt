package com.jennifer.andy.simpleeyes.player.render

import android.annotation.TargetApi
import android.content.Context
import android.graphics.SurfaceTexture
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.TextureView
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.jennifer.andy.simpleeyes.player.MeasureHelper
import com.jennifer.andy.simpleeyes.player.render.IRenderView.IRenderCallback
import com.jennifer.andy.simpleeyes.player.render.IRenderView.ISurfaceHolder
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.ISurfaceTextureHolder
import tv.danmaku.ijk.media.player.ISurfaceTextureHost
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 22:56
 * Description:
 */

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class TextureRenderView : TextureView, IRenderView {

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
        surfaceTextureListener = mSurfaceCallback
    }

    override fun getView(): View {
        return this
    }

    override fun shouldWaitForResize(): Boolean {
        return false
    }

    override fun onDetachedFromWindow() {
        mSurfaceCallback.willDetachFromWindow()
        super.onDetachedFromWindow()
        mSurfaceCallback.didDetachFromWindow()
    }

    //--------------------
    // Layout & Measure
    //--------------------
    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper.setVideoSize(videoWidth, videoHeight)
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
        mMeasureHelper.setVideoRotation(degree)
        rotation = degree.toFloat()
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
    // TextureViewHolder
    //--------------------
    fun getSurfaceHolder(): ISurfaceHolder {
        return InternalSurfaceHolder(this, mSurfaceCallback.mSurfaceTexture, mSurfaceCallback)
    }

    /**
     * 内部surfaceHolder
     */
    private class InternalSurfaceHolder(private val textureView: TextureRenderView,
                                        override val surfaceTexture: SurfaceTexture?,
                                        private val mSurfaceTextureHost: ISurfaceTextureHost) : ISurfaceHolder {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun bindToMediaPlayer(mp: IMediaPlayer?) {
            if (mp == null) return
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                    mp is ISurfaceTextureHolder) {
                val textureHolder = mp as ISurfaceTextureHolder
                textureView.mSurfaceCallback.setOwnSurfaceTexture(false)
                val surfaceTexture = textureHolder.surfaceTexture
                if (surfaceTexture != null) {
                    textureView.surfaceTexture = surfaceTexture
                } else {
                    textureHolder.surfaceTexture = this.surfaceTexture
                    textureHolder.setSurfaceTextureHost(textureView.mSurfaceCallback)
                }
            } else {
                mp.setSurface(openSurface())
            }
        }

        override val renderView: IRenderView
            get() = textureView

        override val surfaceHolder: SurfaceHolder?
            get() = null

        override fun openSurface(): Surface? {
            return if (surfaceTexture == null) null else Surface(surfaceTexture)
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

    /**
     * SurfaceTexture监听
     */
    private class SurfaceCallback(renderView: TextureRenderView) : SurfaceTextureListener, ISurfaceTextureHost {
        var mSurfaceTexture: SurfaceTexture? = null
        private var mIsFormatChanged = false
        private var mWidth = 0
        private var mHeight = 0
        private var mOwnSurfaceTexture = true
        private var mWillDetachFromWindow = false
        private var mDidDetachFromWindow = false
        private val mWeakRenderView: WeakReference<TextureRenderView> = WeakReference(renderView)
        private val mRenderCallbackMap: MutableMap<IRenderCallback, Any> = ConcurrentHashMap()

        fun setOwnSurfaceTexture(ownSurfaceTexture: Boolean) {
            mOwnSurfaceTexture = ownSurfaceTexture
        }

        /**
         * 添加渲染回调
         */
        fun addRenderCallback(callback: IRenderCallback) {
            mRenderCallbackMap[callback] = callback
            var surfaceHolder: ISurfaceHolder? = null
            if (mSurfaceTexture != null) {
                if (surfaceHolder == null) surfaceHolder = InternalSurfaceHolder(mWeakRenderView.get()!!, mSurfaceTexture, this)
                callback.onSurfaceCreated(surfaceHolder, mWidth, mHeight)
            }
            if (mIsFormatChanged) {
                if (surfaceHolder == null) surfaceHolder = InternalSurfaceHolder(mWeakRenderView.get()!!, mSurfaceTexture, this)
                callback.onSurfaceChanged(surfaceHolder, 0, mWidth, mHeight)
            }
        }

        fun removeRenderCallback(callback: IRenderCallback) {
            mRenderCallbackMap.remove(callback)
        }

        /**
         * 当纹理面板可用的时候
         */
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            mSurfaceTexture = surface
            mIsFormatChanged = false
            mWidth = 0
            mHeight = 0
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakRenderView.get()!!, surface, this)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceCreated(surfaceHolder, 0, 0)
            }
        }

        /**
         * 当纹理面板大小发生改变的时候
         */
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            mSurfaceTexture = surface
            mIsFormatChanged = true
            mWidth = width
            mHeight = height
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakRenderView.get()!!, surface, this)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceChanged(surfaceHolder, 0, width, height)
            }
        }

        /**
         * 当表面纹理摧毁的时候
         */
        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            mSurfaceTexture = surface
            mIsFormatChanged = false
            mWidth = 0
            mHeight = 0
            val surfaceHolder: ISurfaceHolder = InternalSurfaceHolder(mWeakRenderView.get()!!, surface, this)
            for (renderCallback in mRenderCallbackMap.keys) {
                renderCallback.onSurfaceDestroyed(surfaceHolder)
            }
            Log.d(TAG, "onSurfaceTextureDestroyed: destroy: $mOwnSurfaceTexture")
            return mOwnSurfaceTexture
        }

        /**
         * 当表面纹理更新的时候
         */
        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

        //-------------------------
        // ISurfaceTextureHost
        //-------------------------
        override fun releaseSurfaceTexture(surfaceTexture: SurfaceTexture) {
            if (surfaceTexture == null) {
                Log.d(TAG, "releaseSurfaceTexture: null")
            } else if (mDidDetachFromWindow) {
                if (surfaceTexture !== mSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): release different SurfaceTexture")
                    surfaceTexture.release()
                } else if (!mOwnSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): release detached SurfaceTexture")
                    surfaceTexture.release()
                } else {
                    Log.d(TAG, "releaseSurfaceTexture: didDetachFromWindow(): already released by TextureView")
                }
            } else if (mWillDetachFromWindow) {
                if (surfaceTexture !== mSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): release different SurfaceTexture")
                    surfaceTexture.release()
                } else if (!mOwnSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): re-attach SurfaceTexture to TextureView")
                    setOwnSurfaceTexture(true)
                } else {
                    Log.d(TAG, "releaseSurfaceTexture: willDetachFromWindow(): will released by TextureView")
                }
            } else {
                if (surfaceTexture !== mSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: alive: release different SurfaceTexture")
                    surfaceTexture.release()
                } else if (!mOwnSurfaceTexture) {
                    Log.d(TAG, "releaseSurfaceTexture: alive: re-attach SurfaceTexture to TextureView")
                    setOwnSurfaceTexture(true)
                } else {
                    Log.d(TAG, "releaseSurfaceTexture: alive: will released by TextureView")
                }
            }
        }

        /**
         * 将要从window消失
         */
        fun willDetachFromWindow() {
            Log.d(TAG, "willDetachFromWindow()")
            mWillDetachFromWindow = true
        }

        /**
         * 已经从window消失
         */
        fun didDetachFromWindow() {
            Log.d(TAG, "didDetachFromWindow()")
            mDidDetachFromWindow = true
        }

    }

    //--------------------
    // Accessibility
    //--------------------
    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = TextureRenderView::class.java.name
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.className = TextureRenderView::class.java.name
    }

    companion object {
        private const val TAG = "TextureRenderView"
    }
}
