package com.cmc.purithm.data.di

import com.cmc.purithm.data.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}