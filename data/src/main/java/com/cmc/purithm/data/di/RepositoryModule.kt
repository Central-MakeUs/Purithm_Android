package com.cmc.purithm.data.di

import com.cmc.purithm.data.repository.AuthRepositoryImpl
import com.cmc.purithm.data.repository.FilterRepositoryImpl
import com.cmc.purithm.data.repository.MemberRepositoryImpl
import com.cmc.purithm.data.repository.PictureRepositoryImpl
import com.cmc.purithm.data.repository.ReviewRepositoryImpl
import com.cmc.purithm.domain.repository.AuthRepository
import com.cmc.purithm.domain.repository.FilterRepository
import com.cmc.purithm.domain.repository.MemberRepository
import com.cmc.purithm.domain.repository.PictureRepository
import com.cmc.purithm.domain.repository.ReviewRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository

    @Singleton
    @Binds
    abstract fun bindFilterRepository(filterRepositoryImpl: FilterRepositoryImpl) : FilterRepository

    @Singleton
    @Binds
    abstract fun bindReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl) : ReviewRepository

    @Singleton
    @Binds
    abstract fun bindPictureRepository(pictureRepositoryImpl: PictureRepositoryImpl) : PictureRepository
}