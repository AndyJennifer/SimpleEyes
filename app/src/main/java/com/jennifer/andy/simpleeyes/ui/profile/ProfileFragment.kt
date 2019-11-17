package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.AndyApplication
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.profile.adapter.ProfileSettingAdapter
import com.jennifer.andy.simpleeyes.ui.profile.presenter.ProfilePresenter
import com.jennifer.andy.simpleeyes.ui.profile.view.ProfileView
import com.jennifer.andy.simpleeyes.utils.bindView

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:我的
 */

class ProfileFragment : BaseFragment<ProfileView, ProfilePresenter>(), ProfileView, View.OnClickListener {

    private val mRecycler: RecyclerView by bindView(R.id.rv_profile_recycler)
    private val mIvAvatar: SimpleDraweeView by bindView(R.id.iv_avatar)
    private val mFlComment: FrameLayout by bindView(R.id.fl_comment_container)


    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment = ProfileFragment()

        private const val MESSAGE = 0  //我的消息
        private const val FOCUS = 1 //我的关注
        private const val CACHE_POSITION = 2//我的缓存
        private const val WATH_HISTORY = 3//观看记录
    }


    override fun initView(savedInstanceState: Bundle?) {

        mIvAvatar.setOnClickListener(this)
        mFlComment.setOnClickListener(this)
        mRecycler.apply {
            val stringArray = AndyApplication.getResource().getStringArray(R.array.profile_setting)
            adapter = ProfileSettingAdapter(arrayListOf(*stringArray)).apply {
                setOnItemClickListener { _, _, position ->
                    when (position) {
                        CACHE_POSITION -> {//我的缓存
                            start(CacheFragment.newInstance())
                        }
                    }
                }
            }
            layoutManager = LinearLayoutManager(context)
        }
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_avatar,
            R.id.fl_comment_container -> {//跳转到登录界面
                ARouter.getInstance().build("/github/Login").navigation()
            }
            else -> {
            }
        }
    }

    override fun getContentViewLayoutId() = R.layout.fragment_profile


}