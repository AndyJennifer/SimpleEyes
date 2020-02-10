package com.jennifer.andy.simpleeyes.ui.common.di

import com.jennifer.andy.simpleeyes.base.data.BaseRemoteDataSource
import com.jennifer.andy.simpleeyes.base.domain.LoadMoreRepository
import com.jennifer.andy.simpleeyes.base.usecase.LoadMoreUseCase
import com.jennifer.andy.simpleeyes.ui.common.CommonRecyclerActivity
import com.jennifer.andy.simpleeyes.ui.common.CommonRecyclerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:07
 * Description:
 */

val commonModel = module {

    single { BaseRemoteDataSource() }
    single { LoadMoreRepository(get()) }
    single { LoadMoreUseCase(get()) }

    scope(named<CommonRecyclerActivity>()) {
        viewModel {
            CommonRecyclerViewModel(get())
        }
    }
}
