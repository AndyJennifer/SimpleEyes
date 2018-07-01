package com.jennifer.andy.simpleeyes.widget.pull.head

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.pull.refresh.PullRefreshView


/**
 * Author:  andy.xwt
 * Date:    2018/6/25 13:16
 * Description: 每日精选刷新头布局，包括内部眼睛的旋转，和文字的变色
 */

class EliteHeaderView : PullRefreshView {

    private val mHeadInner: ImageView by bindView(R.id.iv_head_inner)
    private val mLoadingMessage: CustomFontTextView by bindView(R.id.tv_loading_msg)
    private var mRotationAnimator: ObjectAnimator? = null

    private var mYDistance = 0f

    companion object {
        private const val ROTATION_DAMP = 2f//阻尼系数
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.refresh_daily_elite_header, this, true)
    }

    /**
     * 处理额外的下拉 dy
     */
    override fun handleExtraPullEvent(dy: Float) {
        //处理眼睛旋转
        mHeadInner.rotation = mHeadInner.rotation + (dy.toInt() / ROTATION_DAMP)

    }

    /**
     * 处理有效的下拉 dy
     */
    override fun handleValidPullEvent(dy: Float) {
        //处理文字透明度
        mYDistance += dy
        if (mYDistance >= height - mLoadingMessage.bottom && mYDistance <= height - mLoadingMessage.top) {
            val argbEvaluator = ArgbEvaluator()
            val fraction = Math.abs(mLoadingMessage.bottom - height + mYDistance) / (mLoadingMessage.height)
            val textColor = argbEvaluator.evaluate(fraction, 0, 0xff444444.toInt()) as Int
            mLoadingMessage.setTextColor(textColor)
        }
    }


    override fun doRefresh() {
        doInnerEyeAnimator()
    }


    /**
     * 执行内部眼睛动画
     */
    private fun doInnerEyeAnimator() {
        mRotationAnimator = ObjectAnimator.ofFloat(mHeadInner, "rotation", 0f, 360f)
        mRotationAnimator?.duration = 1000
        mRotationAnimator?.repeatCount = -1
        mRotationAnimator?.start()
    }

    override fun reset() {
        if (mRotationAnimator != null) {
            mRotationAnimator?.cancel()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        reset()
    }
}