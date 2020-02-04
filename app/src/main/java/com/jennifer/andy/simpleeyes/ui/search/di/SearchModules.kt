package com.jennifer.andy.simpleeyes.ui.search.di

import com.jennifer.andy.simpleeyes.ui.search.SearchHotActivity
import com.jennifer.andy.simpleeyes.ui.search.SearchViewModel
import com.jennifer.andy.simpleeyes.ui.search.domain.SearchRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.search.domain.SearchRepository
import com.jennifer.andy.simpleeyes.ui.search.usecase.SearchUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-02-04 23:07
 * Description:
 */

val searchModule = module {

    single { SearchRemoteDataSource() }
    single { SearchRepository(get()) }
    single { SearchUseCase(get()) }

    scope(named<SearchHotActivity>()) {
        viewModel {
            SearchViewModel(get())
        }
    }
}
