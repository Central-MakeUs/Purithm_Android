package com.cmc.purithm.feature.filter.model

import com.cmc.purithm.domain.entity.review.Review

data class FilterReviewListUiModel(
    val avg: Int,
    val reviews: List<FilterReviewItemUiModel>
) {
    companion object {
        fun toUiModel(review: Review): FilterReviewListUiModel {
            return FilterReviewListUiModel(
                avg = review.avg,
                reviews = review.reviews.map {
                    FilterReviewItemUiModel(
                        id = it.id,
                        pureDegree = it.pureDegree,
                        userName = it.userName,
                        createdAt = it.createdAt,
                        thumbnail = it.pictures[0]
                    )
                }
            )
        }
    }
}