package com.jennifer.andy.simpleeyes.ui.follow.di

import com.jennifer.andy.simpleeyes.ui.follow.FollowFragment
import com.jennifer.andy.simpleeyes.ui.follow.FollowViewModel
import com.jennifer.andy.simpleeyes.ui.follow.domain.FollowRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.follow.domain.FollowRepository
import com.jennifer.andy.simpleeyes.ui.follow.usecase.FollowUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-01-05 01:34
 * Description:
 */

val followModule = module {

    single { FollowRemoteDataSource() }
    single { FollowRepository(get()) }
    single { FollowUseCase(get()) }

    scope(named<FollowFragment>()) {
        viewModel {
            FollowViewModel(get())
        }
    }
}