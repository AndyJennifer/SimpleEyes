package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.profile.presenter.CachePresenter
import com.jennifer.andy.simpleeyes.ui.profile.view.CacheView
import com.jennifer.andy.simpleeyes.utils.bindView


/**
 * Author:  andy.xwt
 * Date:    2019-09-12 15:51
 * Description:我的缓存
 */

class CacheFragment : BaseFragment<CacheView, CachePresenter>(), CacheView, View.OnClickListener {

    private val mIvBack: ImageView by bindView(R.id.iv_back)


    companion object {
        @JvmStatic
        fun newInstance(): CacheFragment = CacheFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mIvBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                pop()
            }
            else -> {
            }
        }

        //todo 这里打算用WorkManager来做

    }

    override fun getContentViewLayoutId() = R.layout.fragment_cache

}
