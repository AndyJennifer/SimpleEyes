package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.FragmentVideoLandingBinding
import com.jennifer.andy.simpleeyes.datasource.UserSettingLocalDataSource
import com.jennifer.andy.simpleeyes.player.render.IRenderView.AR_ASPECT_FIT_PARENT
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindFragment
import com.jennifer.andy.simpleeyes.ui.splash.adapter.DEFAULT_SPLASH_VIDEO_COUNT
import com.jennifer.andy.simpleeyes.ui.splash.adapter.SplashVideoFragmentAdapter


/**
 * Author:  andy.xwt
 * Date:    2018/8/3 10:08
 * Description:视频闪屏页 包含中英文口号，当上滑或者滑动到最后一页时会跳转到主界面
 */

class VideoLandingFragment : BaseDataBindFragment<FragmentVideoLandingBinding>() {

    private var mVideoPosition = 0 //记录当前适配播放的位置
    private var isHasPaused = false // 当前适配播放是否停止


    override fun initView(savedInstanceState: Bundle?) {
        initSloganText()
        initSloganFragments()
        setVideoObserver()
        playVideo()
    }

    /**
     * 设置初始标语
     */
    private fun initSloganText() {
        mDataBinding.apply {
            tvSloganEn.printText(resources.getStringArray(R.array.slogan_array_en)[0])
            tvSloganZh.printText(resources.getStringArray(R.array.slogan_array_zh)[0])
        }
    }


    /**
     * 这里我采用的比较暴力的方法，添加了透明的ViewPager来处理文字的切换。
     */
    private fun initSloganFragments() {

        mDataBinding.apply {

            pageIndicatorView.count = DEFAULT_SPLASH_VIDEO_COUNT

            with(viewPager) {
                verticalListener = { goMainActivityThenFinish() }
                horizontalListener = { goMainActivityThenFinish() }
                mDisMissIndex = DEFAULT_SPLASH_VIDEO_COUNT - 1
                adapter = SplashVideoFragmentAdapter(childFragmentManager)

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        pageIndicatorView.setSelected(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageSelected(position: Int) {
                        if (position in 0..3) {
                            tvSloganEn.printText(resources.getStringArray(R.array.slogan_array_en)[position])
                            tvSloganZh.printText(resources.getStringArray(R.array.slogan_array_zh)[position])
                        }
                    }
                })
            }
        }
    }

    private fun setVideoObserver() {
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onVideoResume() {
                if (isHasPaused) {
                    mDataBinding.videoView.seekTo(mVideoPosition)
                    mDataBinding.videoView.resume()
                    isHasPaused = false
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onVideoPause() {
                mVideoPosition = mDataBinding.videoView.currentPosition
                mDataBinding.videoView.pause()
                isHasPaused = true
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onVideoStop() {
                mDataBinding.videoView.stopPlayback()
            }
        })
    }


    private fun playVideo() {
        val path = R.raw.landing
        with(mDataBinding.videoView) {
            setAspectRatio(AR_ASPECT_FIT_PARENT)
            setVideoPath("android.resource://${activity?.packageName}/$path")
            setOnPreparedListener {
                requestFocus()
                seekTo(0)
                start()
            }
            setOnCompletionListener {
                //播放完毕后，循环播放视频
                it.isLooping = true
                start()
            }
        }
    }

    /**
     * 设置用户不是第一次登录,并跳转到主界面
     */
    private fun goMainActivityThenFinish() {
        UserSettingLocalDataSource.isFirstLogin = false
        findNavController().navigate(VideoLandingFragmentDirections.actionVideoLandingFragmentToMainActivity())
        requireActivity().finish()
    }


    override fun getContentViewLayoutId() = R.layout.fragment_video_landing

}