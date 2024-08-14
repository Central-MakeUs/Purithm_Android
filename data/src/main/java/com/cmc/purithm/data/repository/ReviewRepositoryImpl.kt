package com.cmc.purithm.data.repository

import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.dto.review.AddReviewRequestDto
import com.cmc.purithm.data.remote.mapper.checkSuccess
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.ReviewService
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.entity.review.ReviewItem
import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

internal class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun getFilterReview(filterId: Long): Review {
        return HandleApi.callApi { reviewService.getFilterReview(filterId).toDomain() }
    }

    override suspend fun getReviewItem(reviewId: Long): ReviewItem {
        return HandleApi.callApi { reviewService.getReviewItem(reviewId).toDomain() }
    }

    override suspend fun getAllReview(sortedBy : String): List<ReviewItem> {
        return HandleApi.callApi { reviewService.getFeeds(sortedBy = sortedBy).toDomain() }
    }

    override suspend fun addReview(
        filterId: Long,
        pureDegree: Int,
        content: String,
        pictures: List<String>
    ): Long {
        val request = AddReviewRequestDto(filterId, pureDegree, content, pictures)
        return HandleApi.callApi { reviewService.addReview(request).toDomain() }
    }
}