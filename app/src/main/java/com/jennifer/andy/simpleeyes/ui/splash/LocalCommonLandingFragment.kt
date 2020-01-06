package com.jennifer.andy.simpleeyes.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.navigation.fragment.findNavController
import com.jennifer.andy.base.utils.dip2px
import com.jennifer.andy.base.utils.getDateString
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentLocalCoomonLandingBinding
import com.jennifer.andy.simpleeyes.datasource.UserSettingLocalDataSource
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindFragment
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 09:56
 * Description:本地的加载页(上升动画）
 */

class LocalCommonLandingFragment : BaseDataBindFragment<FragmentLocalCoomonLandingBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        //如果用户没有执行上升动画，则执行，反之则执行缩放动画
        if (!UserSettingLocalDataSource.isShowUserAnim) {
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
        val moveY = dip2px(100f)
        ObjectAnimator.ofFloat(mDataBinding.llMoveContainer, "translationY", 0f, -moveY.toFloat()).apply {
            addUpdateListener {
                if (it.currentPlayTime in 600..1500) {
                    mDataBinding.ivHeadOuter.setImageResource(R.drawable.ic_eye_white_outer)
                    mDataBinding.ivHeadInner.setImageResource(R.drawable.ic_eye_white_inner)
                    mDataBinding.tvName.setTextColor(resources.getColor(R.color.gray_B7B9B8))

                } else if (it.currentPlayTime in 1500..2000) {
                    mDataBinding.ivHeadOuter.setImageResource(R.drawable.ic_eye_black_outer)
                    mDataBinding.ivHeadInner.setImageResource(R.drawable.ic_eye_black_inner)
                    mDataBinding.tvName.setTextColor(resources.getColor(R.color.black_444444))
                }
            }
            duration = 2000
        }.start()
    }

    /**
     * 执行背景动画
     */
    private fun doBackgroundAnimator() {
        ValueAnimator.ofArgb(0, 0xffffffff.toInt()).apply {
            addUpdateListener { mDataBinding.rlLoadingContainer.setBackgroundColor(it.animatedValue as Int) }
            doOnEnd { doTextAnimator() }
            duration = 2000
        }.start()
    }

    /**
     * 显示 today 文字与精选
     */
    private fun doTextAnimator() {
        ValueAnimator.ofArgb(0, 0xff444444.toInt()).apply {
            addUpdateListener {
                val color = it.animatedValue as Int
                setTextColor(mDataBinding.tvToday, color)
                setTextColor(mDataBinding.tvDate, color)
                setTextColor(mDataBinding.tvTodayChose, color)
                mDataBinding.tvDate.text = getDateString(Date(), "- yyyy/MM/dd -")
            }

            doOnEnd { doInnerEyeAnimator() }
        }.start()
    }

    private fun setTextColor(textView: TextView, color: Int) {
        textView.visibility = View.VISIBLE
        textView.setTextColor(color)
    }

    /**
     * 执行内部眼睛动画
     */
    private fun doInnerEyeAnimator() {
        ObjectAnimator.ofFloat(mDataBinding.ivHeadInner, "rotation", 0f, 360f).apply {
            doOnEnd {
                goMainActivityThenFinish()
                UserSettingLocalDataSource.isShowUserAnim = true
            }
            duration = 1000
        }.start()
    }

    /**
     * 执行背景缩放动画
     */
    private fun doScaleAnimator() {

        AnimatorSet().apply {
            val scaleX = ObjectAnimator.ofFloat(mDataBinding.ivBackground, "scaleX", 1f, 1.08f)
            val scaleY = ObjectAnimator.ofFloat(mDataBinding.ivBackground, "scaleY", 1f, 1.08f)
            playTogether(scaleX, scaleY)
            doOnEnd {
                goMainActivityThenFinish()
                UserSettingLocalDataSource.isShowUserAnim = true
            }
            duration = 2000
        }.start()
    }


    /**
     * 跳转到主界面，并结束当前Activity
     */
    private fun goMainActivityThenFinish() {
        findNavController().navigate(LocalCommonLandingFragmentDirections.actionLocalCommonLandingFragmentToMainActivity())
        requireActivity().finish()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_local_coomon_landing
}