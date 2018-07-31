package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.feed.presenter.TopicPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.TopicView


/**
 * Author:  andy.xwt
 * Date:    2018/7/31 15:37
 * Description:专题
 */
@Route(path = "/campaign/list")
class TopicActivity : BaseActivity<TopicView, TopicPresenter>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getContentViewLayoutId() = R.layout.activity_topic
}