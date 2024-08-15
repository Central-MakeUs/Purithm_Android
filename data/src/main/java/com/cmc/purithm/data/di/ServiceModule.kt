package com.cmc.purithm.data.di

import com.cmc.purithm.data.remote.service.AuthService
import com.cmc.purithm.data.remote.service.FilterService
import com.cmc.purithm.data.remote.service.MemberService
import com.cmc.purithm.data.remote.service.PictureService
import com.cmc.purithm.data.remote.service.ReviewService
import com.cmc.purithm.data.remote.service.S3Service
import com.cmc.purithm.domain.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @Named("purithm") retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideMemberService(
        @Named("purithm") retrofit: Retrofit
    ): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Provides
    @Singleton
    fun provideFilterService(
        @Named("purithm") retrofit: Retrofit
    ): FilterService {
        return retrofit.create(FilterService::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewService(
        @Named("purithm") retrofit: Retrofit
    ): ReviewService {
        return retrofit.create(ReviewService::class.java)
    }

    @Provides
    @Singleton
    fun providePictureService(
        @Named("purithm") retrofit: Retrofit
    ): PictureService {
        return retrofit.create(PictureService::class.java)
    }

    @Provides
    @Singleton
    fun provideS3Service(
        @Named("s3") retrofit: Retrofit
    ): S3Service {
        return retrofit.create(S3Service::class.java)
    }
}