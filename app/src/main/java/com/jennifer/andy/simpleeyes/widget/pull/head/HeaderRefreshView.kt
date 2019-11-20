package com.jennifer.andy.simpleeyes.widget.pull.head

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.utils.dip2px


/**
 * Author:  andy.xwt
 * Date:    2017/12/13 16:50
 * Description:
 */

class HeaderRefreshView : FrameLayout {

    private val mRefreshContainer by bindView<RelativeLayout>(R.id.rl_refresh_container)
    private val mIvRefresh by bindView<ImageView>(R.id.iv_refresh)
    private var mRotateAnimation: RotateAnimation? = null

    /**
     * 执行刷新阀值 50dp
     */
    companion object {
        private const val REFRESH_THRESHOLD_VALUE = 50f
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.refresh_category_header, this, true)
        mRefreshContainer.background.alpha = 0
        mIvRefresh.imageAlpha = 0
    }

    /**
     * 显示刷新遮罩
     */
    fun showRefreshCover(scrollValue: Int) {
        if (scrollValue in 1..getRefreshThresholdValue()) {
            val percent = (scrollValue.toFloat() / getRefreshThresholdValue())
            mRefreshContainer.background.alpha = (percent * 255).toInt()
            mIvRefresh.imageAlpha = (percent * 255).toInt()
            mIvRefresh.scaleX = percent
            mIvRefresh.scaleY = percent
        } else {
            mRefreshContainer.background.alpha = 255
            mIvRefresh.imageAlpha = 255
            startRefreshAnimation()
        }

    }

    /**
     * 执行刷新动画
     */
    private fun startRefreshAnimation() {
        if (mRotateAnimation == null) {
            mRotateAnimation = RotateAnimation(0f, 360f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                    .apply {
                        interpolator = LinearInterpolator()
                        repeatCount = -1
                        duration = 1000
                    }
            mIvRefresh.startAnimation(mRotateAnimation)
        }
    }

    /**
     * 关闭刷新遮罩
     */
    fun hideRefreshCover() {
        mIvRefresh.clearAnimation()
        mRotateAnimation = null
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 500
            addUpdateListener {
                val animatedValue = (it.animatedValue) as Float
                mRefreshContainer.background.alpha = (animatedValue * 255).toInt()
                mIvRefresh.imageAlpha = animatedValue.toInt()
            }
        }.start()
    }

    /**
     * 获取刷新阀值
     */
    fun getRefreshThresholdValue() = context.dip2px(REFRESH_THRESHOLD_VALUE)


}