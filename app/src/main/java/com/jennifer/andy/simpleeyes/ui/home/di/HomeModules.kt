package com.jennifer.andy.simpleeyes.ui.home.di

import com.jennifer.andy.simpleeyes.ui.home.DailyEliteActivity
import com.jennifer.andy.simpleeyes.ui.home.DailyEliteViewModel
import com.jennifer.andy.simpleeyes.ui.home.HomeFragment
import com.jennifer.andy.simpleeyes.ui.home.HomeViewModel
import com.jennifer.andy.simpleeyes.ui.home.domain.HomeRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.home.domain.HomeRepository
import com.jennifer.andy.simpleeyes.ui.home.usecase.HomeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2019-12-17 21:20
 * Description:
 */

val homeModule = module {

    single { HomeRemoteDataSource() }
    single { HomeRepository(get()) }
    single { HomeUseCase(get()) }

    scope(named<HomeFragment>()) {
        viewModel {
            HomeViewModel(get())
        }
    }
    scope(named<DailyEliteActivity>()) {
        viewModel {
            DailyEliteViewModel(get())
        }
    }
}
