package com.cmc.purithm.domain.entity.member

data class MemberMetaData(
    val hasReview: Boolean = false,
    val hasViewed: Boolean = false,
    val writeReviewId: Long = 0L
)