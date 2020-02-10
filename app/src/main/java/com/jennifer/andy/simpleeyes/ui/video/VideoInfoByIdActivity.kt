package com.jennifer.andy.simpleeyes.ui.video

import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityVideoinfoByIdBinding
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/7/23 14:25
 * Description: 根据视频id获取视频信息
 */

@Route(path = "/AndyJennifer/detail")
class VideoInfoByIdActivity : BaseStateViewActivity<ActivityVideoinfoByIdBinding>() {


    @Autowired
    @JvmField
    var id: String? = null

    private val mVideoViewModel: VideoViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        bindObserve()
        mVideoViewModel.getVideoInfoById(id!!)
    }

    private fun bindObserve() {
        mVideoViewModel.observeVideoInfo()
                .autoDispose(this)
                .subscribe(this::onRelativeVideoInfoStateArrive)
    }

    private fun onRelativeVideoInfoStateArrive(viewState: ViewState<ContentBean>) {
        when (viewState.action) {
            Action.INIT_SUCCESS -> {
                getVideoInfoSuccess(viewState.data!!)
            }

            Action.INIT_FAIL -> {
                showNetError { mVideoViewModel.getVideoInfoById(id!!) }
            }

        }
    }


    private fun getVideoInfoSuccess(contentBean: ContentBean) {

        val bundle = Bundle().apply {
            putSerializable(Extras.VIDEO_INFO, contentBean)
            putSerializable(Extras.VIDEO_LIST_INFO, arrayListOf<Any>())
        }

        ARouter.getInstance()
                .build("/pgc/detail")
                .with(bundle)
                .navigation(this, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        finish()
                    }
                })
    }

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_videoinfo_by_id

}