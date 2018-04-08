package com.jennifer.andy.simpleeyes.ui.search

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.search.adapter.SearchHotAdapter
import com.jennifer.andy.simpleeyes.ui.search.presenter.SearchPresenter
import com.jennifer.andy.simpleeyes.ui.search.view.SearchHotView
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.image.CenterAlignImageSpan
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 10:54
 * Description:搜索界面
 */

class SearchHotActivity : BaseActivity<SearchHotView, SearchPresenter>(), SearchHotView {

    private val mSearchRemind: RelativeLayout by bindView(R.id.rl_search_remind)
    private val mSearchView: SearchView by bindView(R.id.searchView)
    private val mTvCancel: TextView by bindView(R.id.tv_cancel)
    private val multipleStateView: MultipleStateView by bindView(R.id.multiple_state_view)
    private val mRecycler: RecyclerView by bindView(R.id.rv_recycler)

    private lateinit var mHotSearchAdapter: SearchHotAdapter

    override fun initView(savedInstanceState: Bundle?) {
        initSearchView()
        mTvCancel.setOnClickListener { finish() }
        mPresenter.searchHot()
    }

    private fun initSearchView() {
        mSearchView.isIconified = false
        mSearchView.setIconifiedByDefault(false)
        //设置输入框提示文字样式
        val searchComplete = mSearchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        searchComplete.gravity = Gravity.CENTER
        searchComplete.setHintTextColor(resources.getColor(R.color.gray_66A2A2A2))
        searchComplete.textSize = 13f
        searchComplete.hint = getDecoratedHint(searchComplete.hint, getDrawable(R.drawable.ic_action_search_no_padding), 50)
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                multipleStateView.showLoading()
                // todo 隐藏键盘
                mPresenter.searchVideoByWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    /**
     * 设置输入框提示文字
     */
    private fun getDecoratedHint(hintText: CharSequence, searchHintIcon: Drawable, drawableSize: Int): CharSequence {
        searchHintIcon.setBounds(0, 0, drawableSize, drawableSize)
        val ssb = SpannableStringBuilder("   ")
        ssb.setSpan(CenterAlignImageSpan(searchHintIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssb.append(hintText)
        return ssb
    }


    override fun getHotWordSuccess(hotList: MutableList<String>) {
        mHotSearchAdapter = SearchHotAdapter(hotList)
        val flexBoxLayoutManager = FlexboxLayoutManager(mContext, FlexDirection.ROW)
        flexBoxLayoutManager.justifyContent = JustifyContent.CENTER
        mRecycler.layoutManager = flexBoxLayoutManager
        mRecycler.adapter = mHotSearchAdapter
        startContentAnimation()
    }

    /**
     * 进入内容动画
     */
    private fun startContentAnimation() {
        mSearchRemind.visibility = View.VISIBLE
        val valueAnimator = ValueAnimator.ofInt(multipleStateView.measuredHeight, 0)
        valueAnimator.duration = 500
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            multipleStateView.scrollTo(0, value)
        }
        valueAnimator.start()
    }


    override fun showSearchSuccess(andyInfo: AndyInfo) {
        //todo 重新设置adapter
        multipleStateView.showContent()
    }

    override fun showSearchFail(word: String) {
        multipleStateView.showNetError(View.OnClickListener {
            mPresenter.searchVideoByWord(word)
        })
    }

    override fun initPresenter() = SearchPresenter()


    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP

    override fun getContentViewLayoutId() = R.layout.fragment_search_hot


}