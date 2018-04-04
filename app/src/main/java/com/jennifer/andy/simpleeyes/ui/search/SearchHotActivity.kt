package com.jennifer.andy.simpleeyes.ui.search

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Gravity
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.search.presenter.SearchPresenter
import com.jennifer.andy.simpleeyes.ui.search.view.SearchHotView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 10:54
 * Description:搜索界面
 */

class SearchHotActivity : BaseActivity<SearchHotView, SearchPresenter>(), SearchHotView {

    private val mSearchView: SearchView by bindView(R.id.searchView)

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.searchHot()
        mSearchView.isIconified = false


        //设置输入框提示文字样式
        val searchComplete = mSearchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        searchComplete.gravity = Gravity.CENTER
        searchComplete.setHintTextColor(resources.getColor(R.color.gray_66A2A2A2))
        searchComplete.textSize = 13f
        //todo 这里还是用自定义的吧，太烦了

    }



    override fun showSearchSuccess(it: List<String>) {

    }

    override fun initPresenter() = SearchPresenter()


    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP

    override fun getContentViewLayoutId() = R.layout.fragment_search_hot


}