package com.jennifer.andy.simpleeyes.ui.search


import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jennifer.andy.base.utils.dip2px
import com.jennifer.andy.base.utils.showKeyboard
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivitySearchHotBinding
import com.jennifer.andy.simpleeyes.net.entity.AndyInfo
import com.jennifer.andy.simpleeyes.ui.base.BaseStateViewActivity
import com.jennifer.andy.simpleeyes.ui.base.ViewState
import com.jennifer.andy.simpleeyes.ui.base.action.Action
import com.jennifer.andy.simpleeyes.ui.search.adapter.SearchHotAdapter
import com.jennifer.andy.simpleeyes.ui.search.adapter.SearchVideoAdapter
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.widget.CustomLoadMoreView
import com.jennifer.andy.simpleeyes.widget.image.CenterAlignImageSpan
import com.jennifer.andy.simpleeyes.widget.state.MultipleStateView
import com.uber.autodispose.android.lifecycle.autoDispose
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Author:  andy.xwt
 * Date:    2018/4/3 10:54
 * Description:搜索界面
 */

class SearchHotActivity : BaseStateViewActivity<ActivitySearchHotBinding>() {

    private lateinit var mHotSearchAdapter: SearchHotAdapter
    private lateinit var mSearchVideoAdapter: SearchVideoAdapter

    private lateinit var queryWord: String

    private val mSearchViewModel: SearchViewModel by currentScope.viewModel(this)

    override fun initView(savedInstanceState: Bundle?) {

        initSearchView()

        mDataBinding.tvCancel.setOnClickListener { finish() }

        mSearchViewModel.getHotWord()

        mSearchViewModel.observeAndyInfoState()
                .autoDispose(this)
                .subscribe(this::onAndyInfoStateArrive)

        mSearchViewModel.observeStringState()
                .autoDispose(this)
                .subscribe(this::onStringStateArrive)
    }


    private fun initSearchView() {
        with(mDataBinding) {

            searchView.isIconified = false
            searchView.setIconifiedByDefault(false)

            //设置输入框提示文字样式
            val searchComplete = searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
            searchComplete.gravity = Gravity.CENTER
            searchComplete.setHintTextColor(resources.getColor(R.color.gray_66A2A2A2))
            searchComplete.textSize = 13f
            searchComplete.hint = getDecoratedHint(searchComplete.hint, getDrawable(R.drawable.ic_action_search_no_padding), 50)

            //添加搜索监听
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    showKeyboard(false)
                    mSearchViewModel.searchVideoByWord(query)
                    queryWord = query
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }


    /**
     * 设置输入框提示文字
     */
    private fun getDecoratedHint(hintText: CharSequence, searchHintIcon: Drawable, drawableSize: Int) =
            SpannableStringBuilder("   ").apply {
                searchHintIcon.setBounds(0, 0, drawableSize, drawableSize)
                setSpan(CenterAlignImageSpan(searchHintIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                append(hintText)
            }


    private fun onAndyInfoStateArrive(viewState: ViewState<AndyInfo>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                showSearchSuccess(queryWord, viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mSearchViewModel.searchVideoByWord(queryWord) }
            }

            Action.LOAD_MORE_SUCCESS -> {
                loadMoreSuccess(viewState.data!!)
            }
            Action.HAVE_NO_MORE -> {
                showNoMore()
            }
            Action.LOAD_MORE_FAIL -> {
                showNetError { mSearchViewModel.loadMoreAndyInfo() }
            }
        }
    }

    private fun onStringStateArrive(viewState: ViewState<MutableList<String>>) {
        when (viewState.action) {
            Action.INIT -> {
                showLoading()
            }
            Action.INIT_SUCCESS -> {
                showContent()
                getHotWordSuccess(viewState.data!!)
            }
            Action.INIT_FAIL -> {
                showNetError { mSearchViewModel.getHotWord() }
            }
        }
    }


    private fun getHotWordSuccess(hotList: MutableList<String>) {
        setRecyclerMargin()
        startContentAnimation()
        mHotSearchAdapter = SearchHotAdapter(hotList)
        mHotSearchAdapter.setOnItemClickListener { _, _, position ->
            queryWord = mHotSearchAdapter.getItem(position)!!
            showKeyboard(false)
            mSearchViewModel.searchVideoByWord(queryWord)

        }
        val flexBoxLayoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        flexBoxLayoutManager.justifyContent = JustifyContent.CENTER
        mDataBinding.rvSearchRecycler.layoutManager = flexBoxLayoutManager
        mDataBinding.rvSearchRecycler.adapter = mHotSearchAdapter

    }

    /**
     * 进入内容动画
     */
    private fun startContentAnimation() {
        ValueAnimator.ofInt(mDataBinding.multipleStateView.measuredHeight, 0).apply {
            duration = 500
            interpolator = AccelerateInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Int
                mDataBinding.multipleStateView.scrollTo(0, value)
            }
        }.start()
    }


    private fun showSearchSuccess(query: String, andyInfo: AndyInfo) {
        with(mDataBinding) {
            //设置搜索结果
            rlSearchRemind.setSearchResult(query, andyInfo.total)

            //添加了过滤规则,防止搜索的时候崩溃
            mSearchVideoAdapter = SearchVideoAdapter(andyInfo.itemList.filter {
                it.type == SearchVideoAdapter.VIDEO_COLLECTION_WITH_BRIEF ||
                        it.type == SearchVideoAdapter.VIDEO
            })

            //跳转到播放界面
            mSearchVideoAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                val item = mSearchVideoAdapter.getItem(position)
                if (item?.type == SearchVideoAdapter.VIDEO) {
                    VideoDetailActivity.start(this@SearchHotActivity, item.data, arrayListOf(), position)
                }
            }


            if (andyInfo.itemList.size > 0) {
                resetRecyclerMargin()
            } else {
                mSearchVideoAdapter.setEmptyView(R.layout.empty_search_word, mDataBinding.rvSearchRecycler)
            }

            mSearchVideoAdapter.setOnLoadMoreListener({ mSearchViewModel.loadMoreAndyInfo() }, mDataBinding.rvSearchRecycler)
            mSearchVideoAdapter.setLoadMoreView(CustomLoadMoreView())
            rvSearchRecycler.layoutManager = LinearLayoutManager(this@SearchHotActivity)
            rvSearchRecycler.adapter = mSearchVideoAdapter

            //处理没有更多的情况
            if (andyInfo.nextPageUrl == null) {
                mSearchVideoAdapter.loadMoreEnd()
            }
        }

    }

    private fun loadMoreSuccess(andyInfo: AndyInfo) {
        mSearchVideoAdapter.addData(andyInfo.itemList)
        mSearchVideoAdapter.loadMoreComplete()
    }

    private fun showNoMore() {
        mSearchVideoAdapter.loadMoreEnd()
    }


    /**
     * 重置RecyclerMargin
     */
    private fun resetRecyclerMargin() {
        with(mDataBinding) {
            val lp = rvSearchRecycler.layoutParams as RelativeLayout.LayoutParams
            lp.marginEnd = 0
            lp.marginStart = 0
            rvSearchRecycler.layoutParams = lp
        }

    }

    /**
     * 设置RecyclerMargin
     */
    private fun setRecyclerMargin() {
        with(mDataBinding) {
            val lp = rvSearchRecycler.layoutParams as RelativeLayout.LayoutParams
            lp.marginEnd = dip2px(30f)
            lp.marginStart = dip2px(30f)
            rvSearchRecycler.layoutParams = lp
        }
    }


    override fun toggleOverridePendingTransition() = true

    override fun getOverridePendingTransition() = TransitionMode.TOP

    override fun getMultipleStateView(): MultipleStateView = mDataBinding.multipleStateView

    override fun getContentViewLayoutId() = R.layout.activity_search_hot


}