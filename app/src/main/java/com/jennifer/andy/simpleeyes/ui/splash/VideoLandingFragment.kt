package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.VideoView
import com.jennifer.andy.simpleeyes.AndyApplication
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.UserPreferences
import com.jennifer.andy.simpleeyes.ui.MainActivity
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatFragment
import com.jennifer.andy.simpleeyes.ui.splash.adapter.SplashVideoFragmentAdapter
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView
import com.jennifer.andy.simpleeyes.widget.font.CustomFontTypeWriterTextView
import com.jennifer.andy.simpleeyes.widget.viewpager.InterceptVerticalViewPager
import com.rd.PageIndicatorView


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 10:08
 * Description:视频闪屏页 第一次用户安装应用的时候会走
 */

class VideoLandingFragment : BaseAppCompatFragment() {

    private val mVideoView: VideoView by bindView(R.id.video_view)
    private val mViewPager: InterceptVerticalViewPager by bindView(R.id.view_pager)
    private val mTvSloganChinese: CustomFontTypeWriterTextView by bindView(R.id.tv_slogan_zh)
    private val mTvSloganEnglish: CustomFontTypeWriterTextView by bindView(R.id.tv_slogan_en)
    private val mIndicator: PageIndicatorView by bindView(R.id.pageIndicatorView)

    private var mVideoPosition = 0
    private var isHasPaused = false
    private lateinit var mFragmentList: MutableList<SloganFragment>


    companion object {
        @JvmStatic
        fun newInstance(): VideoLandingFragment = VideoLandingFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        initSloganText()
        play()
    }


    /**
     * 这里我采用的比较暴力的方法，主要是不想写事件拦截了，想写的小伙伴，可以自己去写
     */
    private fun initSloganText() {
        //设置初始标语
        mTvSloganEnglish.printText(AndyApplication.getResource().getStringArray(R.array.slogan_array_en)[0])
        mTvSloganChinese.printText(AndyApplication.getResource().getStringArray(R.array.slogan_array_zh)[0])

        mIndicator.count = 4

        mFragmentList = mutableListOf()
        for (position in 0..4) {//这里创建5个是因为要滑动退出
            mFragmentList.add(SloganFragment.newInstance())
        }


        with(mViewPager) {
            verticalListener = { goMainActivity() }
            offscreenPageLimit = mFragmentList.size
            adapter = SplashVideoFragmentAdapter(mFragmentList, childFragmentManager)

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (position == mFragmentList.size - 2 && positionOffset >= 0.5) {
                        goMainActivity()
                    }
                    mIndicator.setSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageSelected(position: Int) {
                    if (position in 0..3) {
                        mTvSloganEnglish.printText(AndyApplication.getResource().getStringArray(R.array.slogan_array_en)[position])
                        mTvSloganChinese.printText(AndyApplication.getResource().getStringArray(R.array.slogan_array_zh)[position])
                    }
                }
            })
        }
    }

    /**
     * 设置用户不是第一次登录,并跳转到主界面
     */
    private fun goMainActivity() {
        UserPreferences.saveUserIsFirstLogin(false)
        readyGoThenKillSelf(MainActivity::class.java)
    }

    private fun play() {
        val path = R.raw.landing
        with(mVideoView) {
            setVideoPath("android.resource://${activity?.packageName}/$path")
            setOnPreparedListener {
                requestFocus()
                setOnCompletionListener {
                    it.isLooping = true
                    start()
                }
                seekTo(0)
                start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isHasPaused) {
            mVideoView.seekTo(mVideoPosition)
            mVideoView.resume()
            isHasPaused = false
        }
    }

    override fun onPause() {
        super.onPause()
        mVideoPosition = mVideoView.currentPosition
        isHasPaused = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mVideoView.stopPlayback()
    }

    override fun getContentViewLayoutId() = R.layout.fragment_video_landing

}