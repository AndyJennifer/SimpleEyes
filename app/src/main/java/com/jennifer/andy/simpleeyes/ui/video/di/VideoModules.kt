package com.jennifer.andy.simpleeyes.ui.video.di

import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.simpleeyes.ui.video.VideoInfoByIdActivity
import com.jennifer.andy.simpleeyes.ui.video.VideoViewModel
import com.jennifer.andy.simpleeyes.ui.video.domain.VideoRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.video.domain.VideoRepository
import com.jennifer.andy.simpleeyes.ui.video.usecase.VideoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:07
 * Description:
 */

val videoModule = module {

    single { VideoRemoteDataSource() }
    single { VideoRepository(get()) }
    single { VideoUseCase(get()) }

    scope(named<VideoDetailActivity>()) {
        viewModel {
            VideoViewModel(get())
        }
    }

    scope(named<VideoInfoByIdActivity>()) {
        viewModel {
            VideoViewModel(get())
        }
    }
}
