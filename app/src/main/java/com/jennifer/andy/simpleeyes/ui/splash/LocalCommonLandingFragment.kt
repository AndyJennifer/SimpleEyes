package com.jennifer.andy.simpleeyes.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.UserPreferences
import com.jennifer.andy.simpleeyes.ui.MainActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatFragment
import com.jennifer.andy.simpleeyes.utils.DensityUtils
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 09:56
 * Description:本地的加载页(上升动画）
 */

class LocalCommonLandingFragment : BaseAppCompatFragment() {

    private val mIvBackground by bindView<SimpleDraweeView>(R.id.iv_background)
    private val mLoadingContainer by bindView<RelativeLayout>(R.id.rl_loading_container)
    private val mMoveContainer by bindView<RelativeLayout>(R.id.ll_move_container)
    private val mHeadOuter by bindView<ImageView>(R.id.iv_head_outer)
    private val mHeadInner by bindView<ImageView>(R.id.iv_head_inner)
    private val mName by bindView<CustomFontTextView>(R.id.tv_name)

    private val mForToday by bindView<CustomFontTextView>(R.id.tv_today)
    private val mDate by bindView<CustomFontTextView>(R.id.tv_date)
    private val mTodayChose by bindView<CustomFontTextView>(R.id.tv_today_chose)


    companion object {
        @JvmStatic
        fun newInstance(): LocalCommonLandingFragment = LocalCommonLandingFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        //如果用户没登录，执行上升动画，否则执行缩放动画
        if (!UserPreferences.getShowUserAnim()) {
            doUpAnimator()
            doBackgroundAnimator()
        } else {
            doScaleAnimator()
        }
    }


    /**
     * 执行上升动画
     */
    private fun doUpAnimator() {
        val moveY = DensityUtils.dip2px(_mActivity, 100f)
        val upAnimator = ObjectAnimator.ofFloat(mMoveContainer, "translationY", 0f, -moveY.toFloat())
        upAnimator.addUpdateListener {
            if (it.currentPlayTime in 600..1500) {
                mHeadOuter.setImageResource(R.drawable.ic_eye_white_outer)
                mHeadInner.setImageResource(R.drawable.ic_eye_white_inner)
                mName.setTextColor(resources.getColor(R.color.gray_B7B9B8))

            } else if (it.currentPlayTime in 1500..2000) {
                mHeadOuter.setImageResource(R.drawable.ic_eye_black_outer)
                mHeadInner.setImageResource(R.drawable.ic_eye_black_inner)

                mName.setTextColor(resources.getColor(R.color.black_444444))
            }

        }
        upAnimator.duration = 2000
        upAnimator.start()
    }

    /**
     * 执行背景动画
     */
    private fun doBackgroundAnimator() {
        val backgroundAnimator = ValueAnimator.ofArgb(0, 0xffffffff.toInt())
        backgroundAnimator.addUpdateListener {
            mLoadingContainer.setBackgroundColor(it.animatedValue as Int)
        }
        backgroundAnimator.doOnEnd { doTextAnimator() }
        backgroundAnimator.duration = 2000
        backgroundAnimator.start()
    }

    /**
     * 显示 today 文字与精选
     */
    private fun doTextAnimator() {
        val alphaAnimator = ValueAnimator.ofArgb(0, 0xff444444.toInt())
        alphaAnimator.addUpdateListener {
            val color = it.animatedValue as Int
            setTextColor(mForToday, color)
            setTextColor(mDate, color)
            setTextColor(mTodayChose, color)
            mDate.text = TimeUtils.getDateString(Date(), "- yyyy/MM/dd -")
        }
        alphaAnimator.duration = 1000
        alphaAnimator.doOnEnd { doInnerEyeAnimator() }

        alphaAnimator.start()
    }

    private fun setTextColor(textView: TextView, color: Int) {
        textView.visibility = View.VISIBLE
        textView.setTextColor(color)
    }

    /**
     * 执行内部眼睛动画
     */
    private fun doInnerEyeAnimator() {
        val rotationAnimator = ObjectAnimator.ofFloat(mHeadInner, "rotation", 0f, 360f)
        rotationAnimator.doOnEnd {
            readyGoThenKillSelf(MainActivity::class.java, null)
            UserPreferences.saveShowUserAnim(true)
        }
        rotationAnimator.duration = 1000
        rotationAnimator.start()
    }

    /**
     * 执行背景缩放动画
     */
    private fun doScaleAnimator() {
        val scaleX = ObjectAnimator.ofFloat(mIvBackground, "scaleX", 1f, 1.08f)
        val scaleY = ObjectAnimator.ofFloat(mIvBackground, "scaleY", 1f, 1.08f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.doOnEnd {
            readyGoThenKillSelf(MainActivity::class.java, null)
            UserPreferences.saveShowUserAnim(true)
        }
        animatorSet.duration = 2000
        animatorSet.start()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_local_coomon_landing
}