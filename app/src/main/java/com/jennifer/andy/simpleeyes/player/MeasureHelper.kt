package com.jennifer.andy.simpleeyes.player

import android.view.View
import com.jennifer.andy.simpleeyes.player.render.IRenderView
import java.lang.ref.WeakReference


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 15:09
 * Description:
 */


/**
 * 测量帮助类，根据宽高比及方向，获得测量后的宽高
 */
class MeasureHelper(view: View) {

    private val mWeakView: WeakReference<View>?
    private var mVideoWidth = 0//视频布局的宽度
    private var mVideoHeight = 0//视频布局的高度
    private var mVideoSarNum = 0 //实际视频的宽

    private var mVideoSarDen = 0//实际视频的高
    private var mVideoRotationDegree = 0 //屏幕旋转度数

    var measuredWidth = 0//测量后的宽
    var measuredHeight = 0 //测量后的高

    private var mCurrentAspectRatio = IRenderView.AR_ASPECT_FIT_PARENT //宽高比例


    val view: View? get() = mWeakView?.get()


    init {
        mWeakView = WeakReference(view)
    }

    fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        mVideoWidth = videoWidth
        mVideoHeight = videoHeight
    }

    fun setVideoSampleAspectRatio(videoSarNum: Int, videoSarDen: Int) {
        mVideoSarNum = videoSarNum
        mVideoSarDen = videoSarDen
    }

    fun setVideoRotation(videoRotationDegree: Int) {
        mVideoRotationDegree = videoRotationDegree
    }

    /**
     * 当 View.onMeasure(int, int) 时调用，用于输出视频校正后的宽高
     *
     * @param widthMeasureSpec  宽度测量规则
     * @param heightMeasureSpec 高度测量规则
     */
    fun doMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec

        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270) {
            //如果屏幕为横屏，将宽高进行对调
            val tempSpec = widthMeasureSpec
            widthMeasureSpec = heightMeasureSpec
            heightMeasureSpec = tempSpec
        }
        //获取父布局宽高
        var width = View.getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mCurrentAspectRatio == IRenderView.AR_MATCH_PARENT) {
            //如果宽高比例是匹配父布局，宽高不变
            width = widthMeasureSpec
            height = heightMeasureSpec
        }

        //设置的视频宽高大于0时
        else if (mVideoWidth > 0 && mVideoHeight > 0) {
            val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
            val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
            val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
            val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

            //当前控件测量模式为包裹模式时，根据视频比例与渲染比例，计算出视频显示的宽高比
            if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
                val specAspectRatio = widthSpecSize.toFloat() / heightSpecSize.toFloat()
                var displayAspectRatio: Float
                when (mCurrentAspectRatio) {
                    IRenderView.AR_16_9_FIT_PARENT -> {
                        displayAspectRatio = 16.0f / 9.0f
                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270)
                            displayAspectRatio = 1.0f / displayAspectRatio
                    }
                    IRenderView.AR_4_3_FIT_PARENT -> {
                        displayAspectRatio = 4.0f / 3.0f
                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270)
                            displayAspectRatio = 1.0f / displayAspectRatio
                    }
                    IRenderView.AR_ASPECT_FIT_PARENT,
                    IRenderView.AR_ASPECT_FILL_PARENT,
                    IRenderView.AR_ASPECT_WRAP_CONTENT -> {
                        displayAspectRatio = mVideoWidth.toFloat() / mVideoHeight.toFloat()
                        if (mVideoSarNum > 0 && mVideoSarDen > 0)
                            displayAspectRatio = displayAspectRatio * mVideoSarNum / mVideoSarDen
                    }
                    else -> {
                        displayAspectRatio = mVideoWidth.toFloat() / mVideoHeight.toFloat()
                        if (mVideoSarNum > 0 && mVideoSarDen > 0)
                            displayAspectRatio = displayAspectRatio * mVideoSarNum / mVideoSarDen
                    }
                }

                val shouldBeWider = displayAspectRatio > specAspectRatio
                when (mCurrentAspectRatio) {

                    //以下三种模式，以大的为固定边，小的边按比例缩放
                    IRenderView.AR_ASPECT_FIT_PARENT,
                    IRenderView.AR_16_9_FIT_PARENT,
                    IRenderView.AR_4_3_FIT_PARENT ->
                        if (shouldBeWider) {
                            //高度偏小，则宽度不变，对高进行校正
                            width = widthSpecSize
                            height = (width / displayAspectRatio).toInt()
                        } else {
                            //宽度偏小，则高度不变，对宽进行校正
                            height = heightSpecSize
                            width = (height * displayAspectRatio).toInt()
                        }

                    //填充模式下，以小的为固定边，大的边按比例缩放
                    IRenderView.AR_ASPECT_FILL_PARENT ->
                        if (shouldBeWider) {
                            //高度偏小，则高度不变，对宽进行校正
                            height = heightSpecSize
                            width = (height * displayAspectRatio).toInt()
                        } else {
                            //宽度偏小，则宽度不变，对高进行校正
                            width = widthSpecSize
                            height = (width / displayAspectRatio).toInt()
                        }

                    //包裹模式，以视频采样的宽高为准。
                    IRenderView.AR_ASPECT_WRAP_CONTENT ->
                        if (shouldBeWider) {
                            //高度偏小，将视频宽度与父布局宽度进行比较，取最小。并重新计算高度
                            width = mVideoWidth.coerceAtMost(widthSpecSize)
                            height = (width / displayAspectRatio).toInt()
                        } else {
                            //宽度偏小，将视频高度与父布局高度进行比较，取最小。重新计算宽度
                            height = mVideoHeight.coerceAtMost(heightSpecSize)
                            width = (height * displayAspectRatio).toInt()
                        }
                    //默认情况下
                    else -> if (shouldBeWider) {
                        width = mVideoWidth.coerceAtMost(widthSpecSize)
                        height = (width / displayAspectRatio).toInt()
                    } else {
                        height = mVideoHeight.coerceAtMost(heightSpecSize)
                        width = (height * displayAspectRatio).toInt()
                    }
                }
            }

            //视频宽高为准确模式下,以小的为固定边，大的边按比例缩放
            else if (widthSpecMode == View.MeasureSpec.EXACTLY && heightSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthSpecSize
                height = heightSpecSize
                if (mVideoWidth * height < width * mVideoHeight) {// videoWidth/videoHeight < width / height
                    //宽度过大，重新调整宽度
                    width = height * mVideoWidth / mVideoHeight
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    //高度过大，重新调整高度
                    height = width * mVideoHeight / mVideoWidth
                }
            }

            // 只有宽在准确模式下，宽度以实际为准，高度不能超过父布局
            else if (widthSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthSpecSize
                height = width * mVideoHeight / mVideoWidth
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize
                }
            }

            //只有高在准确模式模式下，高度以实际为准，宽度不能超过父布局
            else if (heightSpecMode == View.MeasureSpec.EXACTLY) {
                height = heightSpecSize
                width = height * mVideoWidth / mVideoHeight
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize
                }
            }
            // 当宽高都不固定时，使用实际的视频宽高
            else {
                width = mVideoWidth
                height = mVideoHeight
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    //太高，重新校正宽高
                    height = heightSpecSize
                    width = height * mVideoWidth / mVideoHeight
                }
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    //太宽，重新校正宽高
                    width = widthSpecSize
                    height = width * mVideoHeight / mVideoWidth
                }
            }
        } else { // no size yet, just adopt the given spec sizes
        }
        measuredWidth = width
        measuredHeight = height
    }

    fun setAspectRatio(aspectRatio: Int) {
        mCurrentAspectRatio = aspectRatio
    }

}