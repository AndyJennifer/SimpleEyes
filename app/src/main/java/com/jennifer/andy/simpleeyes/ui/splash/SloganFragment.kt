package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentSloganBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindFragment


/**
 * Author:  andy.xwt
 * Date:    2018/5/15 10:51
 * Description: 透明 fragment 用于切换视频中的中英文口号
 */

class SloganFragment : BaseDataBindFragment<FragmentSloganBinding>() {


    companion object {
        @JvmStatic
        fun newInstance() = SloganFragment()
    }


    override fun initViewOnCreated(savedInstanceState: Bundle?) {

    }


    override fun getContentViewLayoutId() = R.layout.fragment_slogan
}