package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.net.Extras
import com.jennifer.andy.simpleeyes.net.entity.AuthorBean
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView


/**
 * Author:  andy.xwt
 * Date:    2018/2/12 15:19
 * Description:
 */

class VideoDetailAuthorView : FrameLayout, View.OnClickListener {

    private lateinit var mIvImage: SimpleDraweeView
    private lateinit var mTvTitle: CustomFontTextView
    private lateinit var mDescription: CustomFontTextView
    private lateinit var mAddFollow: CustomFontTextView


    private lateinit var mTitle: String
    private lateinit var mId: String

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_author_head, this, true)
        mIvImage = findViewById(R.id.iv_image)
        mTvTitle = findViewById(R.id.tv_title)
        mDescription = findViewById(R.id.tv_desc)
        mAddFollow = findViewById(R.id.tv_follow)
        setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val bundle = Bundle().apply {
            putString(Extras.TAB_INDEX, "0")
            putString(Extras.TITLE, mTitle)
            putString(Extras.ID, mId)
        }
        ARouter.getInstance()
                .build("/pgc/detail")
                .with(bundle)
                .navigation()
    }

    /**
     * 设置视频作者信息
     */
    fun setVideoAuthorInfo(author: AuthorBean) {
        mIvImage.setImageURI(author.icon)
        mTvTitle.text = author.name
        mDescription.text = author.description
        mTitle = author.name
        mId = author.id
    }
}