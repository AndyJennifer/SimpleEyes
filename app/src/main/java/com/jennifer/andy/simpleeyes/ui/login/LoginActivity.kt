package com.jennifer.andy.simpleeyes.ui.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityLoginBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity


/**
 * Author:  andy.xwt
 * Date:    2019/1/14 19:22
 * Description:
 */
@Route(path = "/github/Login")
class LoginActivity : BaseDataBindActivity<ActivityLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.ivClose.setOnClickListener { finish() }
    }

    override fun getContentViewLayoutId() = R.layout.activity_login

}