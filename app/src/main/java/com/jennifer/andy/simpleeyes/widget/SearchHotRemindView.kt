package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 14:40
 * Description:热门搜索词提示界面
 */

class SearchHotRemindView : FrameLayout {

    private val mTitle: TextView
    private val mResult: TextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_search_hot_remind_view, this)
        mTitle = findViewById(R.id.tv_title)
        mResult = findViewById(R.id.tv_result)
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