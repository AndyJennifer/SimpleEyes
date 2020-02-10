package com.jennifer.andy.simpleeyes.widget.pull.head

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.base.utils.getElapseTimeForShow
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.base.data.UserSettingLocalDataSource
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTypeWriterTextView


/**
 * Author:  andy.xwt
 * Date:    2018/2/11 18:02
 * Description:包含视频的介绍视频信息，收藏、分享、回复等操作
 */

class VideoDetailHeadView : FrameLayout, View.OnClickListener {

    private lateinit var mTitle: CustomFontTypeWriterTextView
    private lateinit var mTvTime: CustomFontTypeWriterTextView
    private lateinit var mDescription: CustomFontTypeWriterTextView
    private lateinit var mFavorite: CustomFontTextView
    private lateinit var mShare: CustomFontTextView
    private lateinit var mReply: CustomFontTextView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_detail_head, this, true)

        mTitle = findViewById(R.id.tv_title)
        mTvTime = findViewById(R.id.tv_time)
        mDescription = findViewById(R.id.tv_desc)
        mFavorite = findViewById(R.id.tv_favorite)
        mShare = findViewById(R.id.tv_share)
        mReply = findViewById(R.id.tv_reply)

        mFavorite.setOnClickListener(this)
        mShare.setOnClickListener(this)
        mReply.setOnClickListener(this)
        mFavorite.setOnClickListener(this)
    }


    /**
     * 开启动画
     */
    fun startScrollAnimation() {
        val widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        measure(widthSpec, heightSpec)
        ObjectAnimator
                .ofFloat(this, "translationY", -measuredHeight.toFloat(), 0f)
                .apply { duration = 300 }
                .start()
    }

    override fun onClick(v: View) {
        if (UserSettingLocalDataSource.isUserLogin) {//如果用户已经登录
            when (v.id) {
                R.id.tv_favorite -> addFavorite()
                R.id.tv_share -> showShare()
                R.id.tv_reply -> addReply()
                R.id.tv_download -> downloadVideo()
            }
        } else { //如果用户没登录，直接跳转到登录界面
            ARouter.getInstance().build("/github/Login").navigation()
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


    /**
     * 设置收藏次数
     */
    fun setFavoriteCount(count: String) {
        mFavorite.text = count
    }

    /**
     * 设置分享次数
     */
    fun setShareCount(count: String) {
        mShare.text = count
    }

    /**
     * 设置评论个数
     */
    fun setReplayCount(count: String) {
        mReply.text = count
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        mTitle.printText(title)
    }

    /**
     * 设置描述
     */
    fun setDescription(description: String) {
        mDescription.printText(description)
    }

    /**
     * 设置种类与时间
     */
    fun setCategoryAndTime(category: String, duration: Int) {
        val description = "#$category   /   ${getElapseTimeForShow(duration)}"
        mTvTime.text = description
    }

}