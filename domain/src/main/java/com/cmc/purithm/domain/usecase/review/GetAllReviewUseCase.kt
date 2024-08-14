package com.cmc.purithm.domain.usecase.review

import com.cmc.purithm.domain.entity.review.ReviewItem
import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

class GetAllReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(sortedBy : String): List<ReviewItem> {
        return reviewRepository.getAllReview(sortedBy)
    }
}