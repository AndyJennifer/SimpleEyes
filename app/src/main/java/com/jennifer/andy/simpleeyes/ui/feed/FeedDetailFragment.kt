package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import com.jennifer.andy.simpleeyes.ui.base.BaseFragment
import com.jennifer.andy.simpleeyes.ui.feed.presenter.FeedDetailPresenter
import com.jennifer.andy.simpleeyes.ui.feed.view.FeedDetailView


/**
 * Author:  andy.xwt
 * Date:    2018/7/3 11:29
 * Description:
 */

class FeedDetailFragment : BaseFragment<FeedDetailView, FeedDetailPresenter>(),FeedDetailView{


    override fun initPresenter(): FeedDetailPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContentViewLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}