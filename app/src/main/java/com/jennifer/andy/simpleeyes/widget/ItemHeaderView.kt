package com.jennifer.andy.simpleeyes.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.base.utils.getTimeStr
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.net.entity.ContentBean
import com.jennifer.andy.simpleeyes.net.entity.Header
import com.jennifer.andy.simpleeyes.utils.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTextView
import com.jennifer.andy.simpleeyes.widget.font.FontType
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2019-07-14 15:57
 * Description:列表中item对应的header
 */
class ItemHeaderView : FrameLayout {

    private val tvTitle by bindView<CustomFontTextView>(R.id.tv_title)
    private val tvSubTitle by bindView<CustomFontTextView>(R.id.tv_sub_title)
    private val tvDesc by bindView<CustomFontTextView>(R.id.tv_desc)
    private val tvFocus by bindView<CustomFontTextView>(R.id.tv_focus)
    private val ivMore by bindView<ImageView>(R.id.iv_more)
    private val imageView by bindView<SimpleDraweeView>(R.id.iv_source)
    private val mTvDate by bindView<TextView>(R.id.tv_date)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_text, this, true)
    }


    fun setHeader(header: Header, type: String?) {

        with(header) {
            //设置标题
            title?.let {

                //分类就居中
                if (type == "videoCollectionOfHorizontalScrollCard") {
                    tvTitle.gravity = Gravity.CENTER
                    imageView.visibility = View.GONE
                    ivMore.visibility = View.VISIBLE
                } else {
                    tvTitle.gravity = getGravity(header.textAlign)
                }
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = it
                tvTitle.setFontType(font)//默认标题加粗
            }

            //副标题
            subTitle?.let { it ->

                //分类就居中
                if (type == "videoCollectionOfHorizontalScrollCard") {
                    tvSubTitle.gravity = Gravity.CENTER
                    imageView.visibility = View.GONE
                } else {
                    tvSubTitle.gravity = getGravity(header.textAlign)
                }
                tvSubTitle.visibility = View.VISIBLE
                tvSubTitle.text = it
                tvSubTitle.setFontType(subTitleFont)
            }


            //设置图片
            icon?.let {
                with(imageView) {
                    imageView.visibility = View.VISIBLE
                    //判断图片类型
                    when (iconType) {
                        //圆形图片
                        "round" -> imageView.hierarchy.roundingParams = RoundingParams.asCircle()
                        ////正方形音乐带播放按钮类型
                        "squareWithPlayButton" -> hierarchy.setOverlayImage(context.getDrawable(R.drawable.icon_cover_play_button))
                        else -> hierarchy.roundingParams?.roundAsCircle = false
                    }
                    setImageURI(icon)
                }

            }

            //设置发布时间
            time?.let {
                mTvDate.visibility = View.VISIBLE
                mTvDate.text = getTimeStr(Date(time!!))
            }

            //点击关注
            follow?.let {
                tvFocus.visibility = View.VISIBLE
            }
            //设置描述
            description?.let {
                tvDesc.visibility = View.VISIBLE
                tvDesc.text = description
            }


            //跳转到指定界面
            actionUrl?.let {
                setOnClickListener {
                    ARouter.getInstance().build(Uri.parse(header.actionUrl)).navigation()
                }
            }
        }
    }


    fun setAuthorHeader(content: ContentBean) {
        with(content) {
            //设置标题
            title?.let {
                tvTitle.visibility = View.VISIBLE
                tvTitle.text = it
                tvTitle.setFontType(FontType.BOLD)//默认标题加粗
            }


            //设置图片
            icon?.let {
                with(imageView) {
                    imageView.visibility = View.VISIBLE
                    //判断图片类型
                    when (iconType) {
                        //圆形图片
                        "round" -> imageView.hierarchy.roundingParams = RoundingParams.asCircle()
                        ////正方形音乐带播放按钮类型
                        "squareWithPlayButton" -> hierarchy.setOverlayImage(context.getDrawable(R.drawable.icon_cover_play_button))
                        else -> hierarchy.roundingParams?.roundAsCircle = false
                    }
                    setImageURI(icon)
                }

            }

            //点击关注
            follow?.let {
                tvFocus.visibility = View.VISIBLE
            }
            //设置描述
            description?.let {
                tvDesc.visibility = View.VISIBLE
                tvDesc.text = description
            }


            //跳转到指定界面
            actionUrl?.let {
                setOnClickListener {
                    ARouter.getInstance().build(Uri.parse(content.actionUrl)).navigation()
                }
            }
        }
    }

    private fun getGravity(textAlign: String?): Int {
        if (textAlign !== null) {
            when (textAlign) {
                "right" -> return Gravity.END
                "left" -> return Gravity.START
                "middle" -> return Gravity.CENTER
            }
        }
        return Gravity.START
    }


}

