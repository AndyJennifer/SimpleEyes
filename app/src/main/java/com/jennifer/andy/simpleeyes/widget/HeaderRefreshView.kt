package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2017/12/13 16:50
 * Description:
 */

class HeaderRefreshView : FrameLayout {

    private lateinit var mRefreshContainer: RelativeLayout
    private lateinit var mIvRefresh: ImageView
    private var mRotateAnimation: RotateAnimation? = null

    /**
     * 执行刷新阀值 40个像素
     */
    private val VALUE_TO_REFRESH = 60

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_header_refresh, this, true)
        mRefreshContainer = view.findViewById(R.id.rl_refresh_container)
        mIvRefresh = view.findViewById(R.id.iv_refresh)
        mRefreshContainer.background.alpha = 0
        mIvRefresh.imageAlpha = 0
    }

    /**
     * 显示刷新遮罩
     */
    fun showRefreshCover(scrollValue: Int) {
        if (scrollValue in 1..VALUE_TO_REFRESH) {
            val percent = (scrollValue.toFloat() / VALUE_TO_REFRESH.toFloat())
            mRefreshContainer.background.alpha = (percent * 255).toInt()
            mIvRefresh.imageAlpha = (percent * 255).toInt()
            mIvRefresh.scaleX = percent
            mIvRefresh.scaleY = percent
            println("---->$percent")
        } else {
            startRefreshAnimation()
        }

    }

    /**
     * 执行刷新动画
     */
    private fun startRefreshAnimation() {
        if (mRotateAnimation == null) {
            mRotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            mRotateAnimation?.interpolator = LinearInterpolator()
            mRotateAnimation?.repeatCount = -1
            mRotateAnimation?.duration = 1000
            mIvRefresh.startAnimation(mRotateAnimation)
        }
    }

    /**
     * 关闭刷新遮罩
     */
    fun hideRefreshCover() {
        mIvRefresh.clearAnimation()
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.interpolator = LinearInterpolator()
        alphaAnimation.duration = 500
        mIvRefresh.startAnimation(alphaAnimation)
        mRefreshContainer.startAnimation(alphaAnimation)

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                resetAnimationAndAlpha()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

    }

    /**
     * 重置动画与透明度
     */
    private fun resetAnimationAndAlpha() {
        mRotateAnimation = null
        mRefreshContainer.background.alpha = 0
        mIvRefresh.imageAlpha = 0
    }


}