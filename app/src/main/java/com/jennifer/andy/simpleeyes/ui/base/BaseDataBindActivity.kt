package com.jennifer.andy.simpleeyes.ui.base

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jennifer.andy.base.utils.showKeyboard
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.FontType


/**
 * Author:  andy.xwt
 * Date:    2019-11-04 23:40
 * Description:实现了DataBind 的Activity
 */

abstract class BaseDataBindActivity<T : ViewDataBinding> : AppCompatActivity() {


    protected lateinit var mDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras(intent.extras)
        if (getContentViewLayoutId() != 0) {
            mDataBinding = DataBindingUtil.setContentView(this, getContentViewLayoutId())
            mDataBinding.lifecycleOwner = this
            initView(savedInstanceState)
        } else
            throw  IllegalArgumentException("You must set layout id")
    }

    /**
     * 初始化工具栏,默认情况下加粗
     */
    protected fun initToolBar(toolbar: View, title: String? = null, fontType: FontType = FontType.BOLD) {
        val ivBack = toolbar.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            showKeyboard(false)
            finish()
        }

        val tvTitle = toolbar.findViewById<CustomFontTextView>(R.id.tv_title)
        tvTitle.setFontType(fontType)
        tvTitle.text = title

    }

    /**
     * 初始化工具栏，默认情况下加粗
     */
    protected fun initToolBar(toolbar: View, @StringRes id: Int? = null, fontType: FontType = FontType.BOLD) {
        val ivBack = toolbar.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener {
            showKeyboard(false)
            finish()
        }

        val tvTitle = toolbar.findViewById<CustomFontTextView>(R.id.tv_title)
        tvTitle.setFontType(fontType)
        tvTitle.setText(id!!)

    }

    /**
     * 初始化数据
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     *  获取bundle 中的数据
     */
    open fun getBundleExtras(extras: Bundle?) {}

    /**
     * 获取当前布局id
     */
    abstract fun getContentViewLayoutId(): Int
}