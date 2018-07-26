package com.jennifer.andy.simpleeyes.ui.video

import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.video.presenter.VideoInfoByIdPresenter
import com.jennifer.andy.simpleeyes.ui.video.view.VideoInfoByIdView


/**
 * Author:  andy.xwt
 * Date:    2018/7/23 14:25
 * Description: 根据视频id获取视频信息
 */

@Route(path = "/AndyJennifer/detail")
class VideoInfoByIdActivity : BaseActivity<VideoInfoByIdView, VideoInfoByIdPresenter>(), VideoInfoByIdView {

    @Autowired
    @JvmField
    var id: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        mPresenter.getVideoInfoById(id!!)
    }

    override fun getVideoInfoSuccess(contentBean: ContentBean) {
        val bundle = Bundle()
        bundle.putSerializable(Extras.VIDEO_INFO, contentBean)
        bundle.putSerializable(Extras.VIDEO_LIST_INFO, arrayListOf<Any>())
        ARouter.getInstance()
                .build("/pgc/detail")
                .with(bundle)
                .navigation(this, object : NavCallback() {
                    override fun onArrival(postcard: Postcard?) {
                        finish()
                    }
                })
    }


    override fun getContentViewLayoutId() = R.layout.activity_videoinfo_by_id

}