package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.entity.review.ReviewItem

interface ReviewRepository {
    suspend fun getFilterReview(filterId : Long) : Review
    suspend fun getReviewItem(reviewId : Long) : ReviewItem
    suspend fun getAllReview(sortedBy : String) : List<ReviewItem>
    suspend fun addReview(filterId : Long, pureDegree : Int, content : String, pictures : List<String>) : Long
    suspend fun getReviewHistory() : List<ReviewItem>
}