package com.jennifer.andy.simpleeyes.ui.profile

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jennifer.andy.simpleeyes.AndyApplication
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.profile.adapter.ProfileSettingAdapter
import com.jennifer.andy.simpleeyes.ui.profile.presenter.ProfilePresenter
import com.jennifer.andy.simpleeyes.ui.profile.view.ProfileView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView

/**
 * Author:  andy.xwt
 * Date:    2017/9/22 13:51
 * Description:我的
 */

class ProfileFragment : BaseFragment<ProfileView, ProfilePresenter>() {

    private val mRecycler: RecyclerView by bindView(R.id.rv_profile_recycler)

    private lateinit var mProfileSettingAdapter: ProfileSettingAdapter

    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun getBundleExtras(extras: Bundle) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        val stringArray = AndyApplication.getResource().getStringArray(R.array.profile_setting)
        mProfileSettingAdapter = ProfileSettingAdapter(arrayListOf(*stringArray))
        mRecycler.adapter = mProfileSettingAdapter
        mRecycler.layoutManager = LinearLayoutManager(context)
    }


    override fun getContentViewLayoutId() = R.layout.fragment_profile


}