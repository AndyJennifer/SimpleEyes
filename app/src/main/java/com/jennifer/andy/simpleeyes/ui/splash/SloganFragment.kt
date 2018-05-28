package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatFragment


/**
 * Author:  andy.xwt
 * Date:    2018/5/15 10:51
 * Description: 口号fragment 用于切换视频口号
 */

class SloganFragment : BaseAppCompatFragment() {


    companion object {
        fun newInstance() = SloganFragment()


    }


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun getContentViewLayoutId() = R.layout.fragment_slogan
}