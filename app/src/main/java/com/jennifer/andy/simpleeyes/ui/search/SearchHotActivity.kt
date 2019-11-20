package com.jennifer.andy.simpleeyes.ui.search


import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseActivity
import com.jennifer.andy.simpleeyes.ui.search.adapter.SearchHotAdapter
import com.jennifer.andy.simpleeyes.ui.search.adapter.SearchVideoAdapter
import com.jennifer.andy.simpleeyes.ui.search.presenter.SearchPresenter
import com.jennifer.andy.simpleeyes.ui.search.view.SearchHotView
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.utils.dip2px
import com.jennifer.andy.simpleeyes.utils.showKeyboard
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.SearchHotRemindView
import com.jennifer.andy.simpleeyes.widget.image.CenterAlignImageSpan
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 10:54
 * Description:搜索界面
 */

class SearchHotActivity : BaseActivity<SearchHotView, SearchPresenter>(), SearchHotView {

    private val mSearchRemind: SearchHotRemindView by bindView(R.id.rl_search_remind)
    private val mSearchView: SearchView by bindView(R.id.searchView)
    private val mTvCancel: TextView by bindView(R.id.tv_cancel)
    private val multipleStateView: MultipleStateView by bindView(R.id.multiple_state_view)
    private val mRecycler: RecyclerView by bindView(R.id.rv_search_recycler)

    private lateinit var mHotSearchAdapter: SearchHotAdapter
    private lateinit var mSearchVideoAdapter: SearchVideoAdapter

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
        //添加搜索监听
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showKeyboard(false)
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
        setRecyclerMargin()
        startContentAnimation()
        mHotSearchAdapter = SearchHotAdapter(hotList)
        mHotSearchAdapter.setOnItemClickListener { _, _, position ->
            showKeyboard(false)
            mPresenter.searchVideoByWord(mHotSearchAdapter.getItem(position)!!)
        }
        val flexBoxLayoutManager = FlexboxLayoutManager(mContext, FlexDirection.ROW)
        flexBoxLayoutManager.justifyContent = JustifyContent.CENTER
        mRecycler.layoutManager = flexBoxLayoutManager
        mRecycler.adapter = mHotSearchAdapter

    }

    /**
     * 进入内容动画
     */
    private fun startContentAnimation() {
        val valueAnimator = ValueAnimator.ofInt(multipleStateView.measuredHeight, 0)
        valueAnimator.duration = 500
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            multipleStateView.scrollTo(0, value)
        }
        valueAnimator.start()
    }


    override fun showSearchSuccess(queryWord: String, andyInfo: AndyInfo) {

        //设置搜索结果
        mSearchRemind.setSearchResult(queryWord, andyInfo.total)

        //添加了过滤规则,防止搜索的时候崩溃
        mSearchVideoAdapter = SearchVideoAdapter(andyInfo.itemList.filter { it.type == SearchVideoAdapter.VIDEO_COLLECTION_WITH_BRIEF || it.type == SearchVideoAdapter.VIDEO })


        //跳转到播放界面
        mSearchVideoAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            val item = mSearchVideoAdapter.getItem(position)
            if (item?.type == SearchVideoAdapter.VIDEO) {
                VideoDetailActivity.start(mContext, item.data, arrayListOf(), position)
            }
        }


        if (andyInfo.itemList.size > 0) {
            resetRecyclerMargin()
        } else {
            mSearchVideoAdapter.setEmptyView(R.layout.empty_search_word, mRecycler)
        }

        mSearchVideoAdapter.setOnLoadMoreListener({ mPresenter.loadMoreInfo() }, mRecycler)
        mSearchVideoAdapter.setLoadMoreView(CustomLoadMoreView())
        mRecycler.layoutManager = LinearLayoutManager(mContext)
        mRecycler.adapter = mSearchVideoAdapter

        //处理没有更多的情况
        if (andyInfo.nextPageUrl == null) {
            mSearchVideoAdapter.loadMoreEnd()
        }

    }

    override fun loadMoreSuccess(andyInfo: AndyInfo) {
        mSearchVideoAdapter.addData(andyInfo.itemList)
        mSearchVideoAdapter.loadMoreComplete()
    }

    override fun showNoMore() {
        mSearchVideoAdapter.loadMoreEnd()
    }

    override fun showLoading() {
        multipleStateView.showLoading()
    }

    override fun showContent() {
        multipleStateView.showContent()
    }

    override fun showNetError(onClickListener: View.OnClickListener) {
        multipleStateView.showNetError(onClickListener)
    }


    /**
     * 重置RecyclerMargin
     */
    private fun resetRecyclerMargin() {
        val lp = mRecycler.layoutParams as RelativeLayout.LayoutParams
        lp.marginEnd = 0
        lp.marginStart = 0
        mRecycler.layoutParams = lp
    }

    /**
     * 设置RecyclerMargin
     */
    private fun setRecyclerMargin() {
        val lp = mRecycler.layoutParams as RelativeLayout.LayoutParams
        lp.marginEnd = dip2px(30f)
        lp.marginStart = dip2px(30f)
        mRecycler.layoutParams = lp
    }


    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP

    override fun getContentViewLayoutId() = R.layout.fragment_search_hot


}