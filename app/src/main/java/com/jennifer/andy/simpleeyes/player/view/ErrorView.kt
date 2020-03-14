package com.jennifer.andy.simpleeyes.player.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.player.IjkMediaController


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 9:19 PM
 * Description: 错误界面，用于[ControllerView]显示的网络错误界面。
 */
class ErrorView(context: Context) : FrameLayout(context), View.OnClickListener {

    private val mIvReload: ImageView
    private lateinit var mController: IjkMediaController

    fun setController(controller: IjkMediaController) {
        mController = controller
    }

    override fun onClick(v: View) {
        val rotation = ObjectAnimator.ofFloat(mIvReload, "rotation", 0f, 360f)
        rotation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) { //执行选中动画，然后开始刷新操作
                mController.hide()
                mController.controllerListener?.onErrorViewClick()
            }
        })
        rotation.duration = 500
        rotation.start()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_video_error, this, true)
        mIvReload = findViewById(R.id.iv_reload)
        setOnClickListener(this)
    }
}