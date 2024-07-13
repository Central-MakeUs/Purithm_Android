package com.cmc.purithm.data.di

import android.content.Context
import com.cmc.purithm.data.local.datasource.AuthDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object LocalModule {
    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context : Context
    ) = AuthDataStore(context)
}