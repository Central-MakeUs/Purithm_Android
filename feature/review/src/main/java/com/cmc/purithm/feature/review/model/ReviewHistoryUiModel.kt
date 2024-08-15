package com.cmc.purithm.feature.review.model

import com.cmc.purithm.domain.entity.review.ReviewItem

/**
 * filterName, thumbnail은 navigation argument로 가져옴
 * */
data class ReviewHistoryUiModel(
    val id: Long,
    val pureDegree: Int,
    val content: String,
    val userName: String,
    val userProfile: String,
    val pictures: List<String>
) {
    companion object {
        fun toUiModel(data: ReviewItem): ReviewHistoryUiModel {
            return ReviewHistoryUiModel(
                id = data.id,
                pureDegree = data.pureDegree,
                content = data.content,
                userName = data.userName,
                userProfile = data.userProfile,
                pictures = data.pictures ?: emptyList()
            )
        }
    }
}