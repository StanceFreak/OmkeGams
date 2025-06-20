package com.stancefreak.pihakaseng.di

import com.stancefreak.pihakaseng.repository.AppRepository
import com.stancefreak.pihakaseng.view.detail.DetailViewModel
import com.stancefreak.pihakaseng.view.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun homeViewModel(repository: AppRepository) = HomeViewModel(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModel(repository: AppRepository) = DetailViewModel(repository)
}