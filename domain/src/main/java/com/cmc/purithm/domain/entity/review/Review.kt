package com.cmc.purithm.domain.entity.review

data class Review(
    val avg: Int = 0,
    val reviews: List<ReviewItem> = emptyList()
)