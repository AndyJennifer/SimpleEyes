package com.jennifer.andy.simpleeyes.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.MainActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.utils.DensityUtils
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2018/2/3 10:26
 * Description:闪屏页
 */

class SplashActivity : BaseAppCompatActivity() {

    private val mLoadingContainer by bindView<RelativeLayout>(R.id.rl_loading_container)
    private val mMoveContainer by bindView<RelativeLayout>(R.id.ll_move_container)
    private val mHeadOuter by bindView<ImageView>(R.id.iv_head_outer)
    private val mHeadInner by bindView<ImageView>(R.id.iv_head_inner)
    private val mName by bindView<CustomFontTextView>(R.id.tv_name)

    private val mForToday by bindView<CustomFontTextView>(R.id.tv_today)
    private val mDate by bindView<CustomFontTextView>(R.id.tv_date)
    private val mTodayChose by bindView<CustomFontTextView>(R.id.tv_today_chose)


    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        doUpAnimator()
        doBackgroundAnimator()
    }


    /**
     * 执行上升动画
     */
    private fun doUpAnimator() {
        val moveY = DensityUtils.dip2px(this, 100f)
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
        backgroundAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                doTextAnimator()
            }
        })
        backgroundAnimator.duration = 2000
        backgroundAnimator.start()
    }

    /**
     * 显示 today 文字与精选
     */
    private fun doTextAnimator() {
        val alphaAnimator = ValueAnimator.ofArgb(0, 0xff444444.toInt())
        alphaAnimator.addUpdateListener {
            var color = it.animatedValue as Int
            setTextColor(mForToday, color)
            setTextColor(mDate, color)
            setTextColor(mTodayChose, color)
            mDate.text = TimeUtils.getDateString(Date(), "- yyyy/MM/dd -")
        }
        alphaAnimator.duration = 1000
        alphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                doInnerEyeAnimator()
            }
        })
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
        rotationAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                readyGoThenKillSelf(MainActivity::class.java, null)
            }
        })
        rotationAnimator.duration = 1000
        rotationAnimator.start()
    }


    override fun getContentViewLayoutId() = R.layout.activity_splash
}