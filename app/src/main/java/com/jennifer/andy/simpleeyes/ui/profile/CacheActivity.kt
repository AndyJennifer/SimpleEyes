package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import android.view.View
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityCacheBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity


/**
 * Author:  andy.xwt
 * Date:    2019-09-12 15:51
 * Description:我的缓存
 */

class CacheActivity : BaseDataBindActivity<ActivityCacheBinding>(), View.OnClickListener {

    companion object {
        fun newInstance() = CacheActivity()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDataBinding.ivBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                finish()
            }
            else -> {
            }
        }

        //todo 这里打算用WorkManager来做

    }


    override fun getContentViewLayoutId() = R.layout.activity_cache

}
