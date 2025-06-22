package com.stancefreak.pihakaseng.di

import android.content.Context
import com.stancefreak.pihakaseng.local.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesPrefManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)

}