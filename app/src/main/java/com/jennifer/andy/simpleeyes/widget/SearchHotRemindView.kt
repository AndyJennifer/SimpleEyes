package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 14:40
 * Description:热门搜索词提示界面
 */

class SearchHotRemindView : FrameLayout {

    private val mTitle: TextView by bindView(R.id.tv_title)
    private val mResult: TextView by bindView(R.id.tv_result)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_search_hot_remind_view, this)
    }

    /**
     * 设置搜索结果
     * @param queryWord 搜索内容
     * @param count 搜索个数
     */
    fun setSearchResult(queryWord: CharSequence, count: Int) {
        mTitle.visibility = View.GONE
        if (count > 0) {
            mResult.text = "- [ $queryWord ] 搜索结果共${count}个 -"
            mResult.visibility = View.VISIBLE
        } else {
            mResult.visibility = View.GONE

        }
    }

}