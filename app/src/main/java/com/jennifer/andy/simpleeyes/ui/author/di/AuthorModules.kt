package com.jennifer.andy.simpleeyes.ui.author.di

import com.jennifer.andy.simpleeyes.ui.author.AuthorTagDetailActivity
import com.jennifer.andy.simpleeyes.ui.author.AuthorTagViewModel
import com.jennifer.andy.simpleeyes.ui.author.domain.AuthorRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.author.domain.AuthorRepository
import com.jennifer.andy.simpleeyes.ui.author.usecase.AuthorUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-01-19 14:27
 * Description:
 */

val authorModel = module {

    single { AuthorRemoteDataSource() }
    single { AuthorRepository(get()) }
    single { AuthorUseCase(get()) }

    scope(named<AuthorTagDetailActivity>()) {
        viewModel {
            AuthorTagViewModel(get())
        }
    }
}
