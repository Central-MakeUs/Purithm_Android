package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.review.Review

interface ReviewRepository {
    suspend fun getFilterReview(filterId : Long) : Review
}