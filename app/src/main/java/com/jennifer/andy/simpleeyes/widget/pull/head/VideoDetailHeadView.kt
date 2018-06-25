package com.jennifer.andy.simpleeyes.widget.pull.head

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.utils.TimeUtils
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTypeWriterTextView


/**
 * Author:  andy.xwt
 * Date:    2018/2/11 18:02
 * Description:
 */

class VideoDetailHeadView : FrameLayout, View.OnClickListener {

    private val mTitle by bindView<CustomFontTypeWriterTextView>(R.id.tv_title)
    private val mTvTime by bindView<CustomFontTypeWriterTextView>(R.id.tv_time)
    private val mDescription by bindView<CustomFontTypeWriterTextView>(R.id.tv_desc)

    private val mFavorite by bindView<CustomFontTextView>(R.id.tv_favorite)
    private val mShare by bindView<CustomFontTextView>(R.id.tv_share)
    private val mReply by bindView<CustomFontTextView>(R.id.tv_reply)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_video_detail_head, this, true)
        mFavorite.setOnClickListener(this)
        mShare.setOnClickListener(this)
        mReply.setOnClickListener(this)
        mFavorite.setOnClickListener(this)
    }


    /**
     * 开启动画
     */
    fun startScrollAnimation() {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        measure(widthSpec, heightSpec)
        val animator = ObjectAnimator.ofFloat(this, "translationY", -measuredHeight.toFloat(), 0f)
        animator.duration = 300
        animator.start()
    }

    override fun onClick(v: View) {
        when (v.id) {
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
        val description = "#$category   /   ${TimeUtils.getElapseTimeForShow(duration)}"
        mTvTime.text = description
    }

}