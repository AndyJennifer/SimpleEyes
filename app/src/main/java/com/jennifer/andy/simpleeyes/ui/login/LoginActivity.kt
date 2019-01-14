package com.jennifer.andy.simpleeyes.ui.login

import android.os.Bundle
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2019/1/14 19:22
 * Description:
 */
@Route(path = "/github/Login")
class LoginActivity : BaseAppCompatActivity() {

    private val mIvClose: ImageView by bindView(R.id.iv_close)


    override fun initView(savedInstanceState: Bundle?) {
        mIvClose.setOnClickListener { finish() }
    }

    override fun getContentViewLayoutId() = R.layout.activity_login

}