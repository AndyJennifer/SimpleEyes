package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTypeWriterTextView


/**
 * Author:  andy.xwt
 * Date:    2017/12/28 17:12
 * Description:视频信息，头部界面
 */

class VideoHeadView : FrameLayout, View.OnClickListener {

    private lateinit var mTitle: CustomFontTypeWriterTextView
    private lateinit var mTime: CustomFontTypeWriterTextView
    private lateinit var mDescription: CustomFontTypeWriterTextView
    private lateinit var mFavorite: CustomFontTypeWriterTextView
    private lateinit var mShare: CustomFontTypeWriterTextView
    private lateinit var mReply: CustomFontTypeWriterTextView
    private lateinit var mDownload: CustomFontTypeWriterTextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_video_head_view, this, true)
        mTitle = view.findViewById(R.id.tv_title)
        mTime = view.findViewById(R.id.tv_time)
        mDescription = view.findViewById(R.id.tv_desc)
        mFavorite = view.findViewById(R.id.tv_favorite)
        mShare = view.findViewById(R.id.tv_share)
        mReply = view.findViewById(R.id.tv_reply)
        mDownload = view.findViewById(R.id.tv_download)
        mFavorite.setOnClickListener(this)
        mShare.setOnClickListener(this)
        mReply.setOnClickListener(this)
        mFavorite.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_favorite -> addFavorite()
            R.id.tv_share -> showShare()
            R.id.tv_reply -> addReply()
            R.id.tv_download -> downloadVideo()
        }
    }

    /**
     * 添加收藏
     */
    private fun addFavorite() {


    }

    /**
     * 显示分享
     */
    private fun showShare() {

    }

    /**
     * 添加回复
     */
    private fun addReply() {

    }

    /**
     * 下载视频
     */
    private fun downloadVideo() {


    }


}