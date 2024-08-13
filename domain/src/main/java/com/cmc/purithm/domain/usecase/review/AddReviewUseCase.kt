package com.cmc.purithm.domain.usecase.review

import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(
        filterId: Long,
        pureDegree: Int,
        content: String,
        pictures: List<String>
    ): Long {
        return reviewRepository.addReview(filterId, pureDegree, content, pictures)
    }
}