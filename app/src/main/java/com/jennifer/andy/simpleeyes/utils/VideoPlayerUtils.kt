package com.jennifer.andy.simpleeyes.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ContextThemeWrapper
import android.view.Window
import android.view.WindowManager


/**
 * Author:  andy.xwt
 * Date:    2018/2/20 17:33
 * Description:
 */

object VideoPlayerUtils {

    /**
     * 隐藏ActionBar并使当前界面全屏
     */
    @JvmStatic
    fun hideActionBar(context: Context?) {
        val supportActionBar = getAppCompatActivity(context)?.supportActionBar
        supportActionBar?.let {
            supportActionBar.setShowHideAnimationEnabled(false)
            supportActionBar.hide()
        }
        getActivity(context)?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 显示ActionBar并退出全屏
     */
    @JvmStatic
    fun showActionBar(context: Context?) {
        val supportActionBar = getAppCompatActivity(context)?.supportActionBar
        supportActionBar?.let {
            supportActionBar.setShowHideAnimationEnabled(false)
            supportActionBar.show()
        }
        getActivity(context)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 获取AppCompatActivity
     */
    private fun getAppCompatActivity(context: Context?): AppCompatActivity? {
        if (context == null) {
            return null
        }
        if (context is AppCompatActivity) {
            return context
        } else if (context is ContextThemeWrapper) {
            return getAppCompatActivity(context.baseContext)
        }
        return null
    }

    /**
     * 获取Activity
     */
    @JvmStatic
    fun getActivity(context: Context?): Activity? {
        if (context == null) {
            return null
        }
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return getActivity(context.baseContext)
        }
        return null
    }

    /**
     * 获取Window
     */
    @JvmStatic
    fun getWindow(context: Context?): Window? {
        return if (getAppCompatActivity(context) != null) {
            getAppCompatActivity(context)?.window
        } else {
            getActivity(context)?.window
        }
    }

}