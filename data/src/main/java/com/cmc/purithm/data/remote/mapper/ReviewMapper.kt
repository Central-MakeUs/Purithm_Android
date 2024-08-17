package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.review.AddReviewResponseDto
import com.cmc.purithm.data.remote.dto.review.GetFilterReviewResponseDto
import com.cmc.purithm.data.remote.dto.review.ReviewResponseDto
import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.entity.member.MemberMetaData
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.entity.review.ReviewItem

internal fun BaseResponse<GetFilterReviewResponseDto>.toDomain(): Review {
    val response = this.data ?: throw NullPointerException("data is null")
    return Review(
        avg = response.avg,
        memberMetaData = MemberMetaData(
            hasViewed = response.hasViewed,
            hasReview = response.hasReview,
            writeReviewId = response.reviewId ?: 0
        ),
        reviews = response.reviews.map {
            ReviewItem(
                id = it.id,
                content = it.content,
                pureDegree = it.pureDegree,
                userProfile = it.profile,
                userName = it.userName,
                pictures = it.pictures ?: emptyList(),
                createdAt = it.createAt
            )
        }
    )
}

internal fun BaseResponse<ReviewResponseDto>.toDomain(): ReviewItem {
    val response = this.data ?: throw NullPointerException("data is null")
    return ReviewItem(
        pureDegree = response.pureDegree,
        content = response.content,
        userProfile = response.userProfile,
        userName = response.username,
        pictures = response.pictures,
    )
}

internal fun BaseResponse<AddReviewResponseDto>.toDomain(): Long {
    val response = this.data ?: throw NullPointerException("data is null")
    return response.id

}