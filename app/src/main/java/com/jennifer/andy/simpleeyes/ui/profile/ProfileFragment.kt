package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.utils.readyGo
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentProfileBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindFragment
import com.jennifer.andy.simpleeyes.ui.profile.adapter.ProfileSettingAdapter

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:我的
 */

class ProfileFragment : BaseDataBindFragment<FragmentProfileBinding>(), View.OnClickListener {

    companion object {
        fun newInstance() = ProfileFragment()

        private const val MESSAGE = 0 //我的消息
        private const val FOCUS = 1 //我的关注
        private const val CACHE = 2//我的缓存
        private const val WATCH_HISTORY = 3//观看记录
    }

    override fun initViewOnCreated(savedInstanceState: Bundle?) {
        with(mDataBinding) {
            ivAvatar.setOnClickListener(this@ProfileFragment)
            flCommentContainer.setOnClickListener(this@ProfileFragment)

            rvProfileRecycler.apply {
                val stringArray = resources.getStringArray(R.array.profile_setting)
                adapter = ProfileSettingAdapter(arrayListOf(*stringArray))
                        .apply {
                            setOnItemClickListener { _, _, position ->
                                when (position) {
                                    MESSAGE -> {//我的消息

                                    }
                                    FOCUS -> {//我的关注

                                    }
                                    CACHE -> {//我的缓存
                                        readyGo<CacheActivity>()
                                    }
                                    WATCH_HISTORY -> {//观看记录

                                    }
                                }
                            }
                        }
                layoutManager = LinearLayoutManager(context)
            }
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_avatar,
            R.id.fl_comment_container -> {//跳转到登录界面
                ARouter.getInstance().build("/github/Login").navigation()
            }
        }
    }

    override fun getContentViewLayoutId() = R.layout.fragment_profile


}