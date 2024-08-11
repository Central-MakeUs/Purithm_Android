package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.review.GetFilterReviewResponseDto
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.entity.review.ReviewItem

internal fun BaseResponse<GetFilterReviewResponseDto>.toDomain(): Review {
    val response = this.data ?: throw NullPointerException("data is null")
    return Review(
        avg = response.avg,
        reviews = response.reviews.map {
            ReviewItem(
                id = it.id,
                pureDegree = it.pureDegree,
                userName = it.userName,
                pictures = it.pictures,
                createdAt = it.createAt
            )
        }
    )
}