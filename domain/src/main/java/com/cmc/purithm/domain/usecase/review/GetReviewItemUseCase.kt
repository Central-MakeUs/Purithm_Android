package com.cmc.purithm.domain.usecase.review

import com.cmc.purithm.domain.entity.review.ReviewItem
import com.cmc.purithm.domain.repository.ReviewRepository
import javax.inject.Inject

class GetReviewItemUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
){
    suspend operator fun invoke(reviewId : Long) : ReviewItem {
        return reviewRepository.getReviewItem(reviewId)
    }
}