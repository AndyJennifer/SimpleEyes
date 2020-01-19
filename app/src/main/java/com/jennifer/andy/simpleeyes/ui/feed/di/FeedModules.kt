package com.jennifer.andy.simpleeyes.ui.feed.di

import com.jennifer.andy.simpleeyes.ui.feed.*
import com.jennifer.andy.simpleeyes.ui.feed.domain.FeedRemoteDataSource
import com.jennifer.andy.simpleeyes.ui.feed.domain.FeedRepository
import com.jennifer.andy.simpleeyes.ui.feed.usecase.FeedUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


/**
 * Author:  andy.xwt
 * Date:    2020-01-02 21:21
 * Description:
 */

val feedModel = module {

    single { FeedRemoteDataSource() }
    single { FeedRepository(get()) }
    single { FeedUseCase(get()) }

    scope(named<FeedFragment>()) {
        viewModel {
            FeedViewModel(get())
        }
    }

    scope(named<AllCategoryActivity>()) {
        viewModel { AllCategoryViewModel(get()) }
    }

    scope(named<CategoryTabActivity>()) {
        viewModel { CategoryTabViewModel(get()) }
    }

    scope(named<TagDetailInfoFragment>()) {
        viewModel { TagDetailViewModel(get()) }
    }

    scope(named<RankListActivity>()) {
        viewModel { RankListViewModel(get()) }
    }
    scope(named<TopicActivity>()) {
        viewModel { TopicViewModel(get()) }
    }
}
