package com.cmc.purithm.domain.usecase.review

import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

class GetFilterReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(filterId: Long): Review {
        return reviewRepository.getFilterReview(filterId)
    }
}