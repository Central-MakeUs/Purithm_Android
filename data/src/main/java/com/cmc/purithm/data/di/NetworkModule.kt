package com.cmc.purithm.data.di

import com.cmc.purithm.data.local.datasource.AuthDataStore
import com.cmc.purithm.data.remote.ApiConfig
import com.cmc.purithm.data.remote.interceptor.AddTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Singleton
    @Provides
    @Named("purithm")
    fun provideOsdsRetrofit(
        @Named("authInterceptor") interceptor : OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(interceptor)
        .build()

    @Singleton
    @Provides
    @Named("authInterceptor")
    fun provideAuthOkHttpClient(
        authDataStore : AuthDataStore
    ) = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(httpLoggingInterceptor())
        .addNetworkInterceptor(AddTokenInterceptor(authDataStore))
        .build()

    @Singleton
    @Provides
    @Named("noAuthInterceptor")
    fun provideNoAuthOkHttpClient() = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(httpLoggingInterceptor())
        .build()

    private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}