package com.cmc.purithm.data.repository

import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.ReviewService
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

internal class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun getFilterReview(filterId: Long): Review {
        return HandleApi.callApi { reviewService.getFilterReview(filterId).toDomain() }
    }
}