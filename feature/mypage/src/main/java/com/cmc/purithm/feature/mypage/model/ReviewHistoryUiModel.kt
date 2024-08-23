package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.domain.entity.review.ReviewItem

data class ReviewHistoryUiModel(
    val id: Long,
    val filterId: Long,
    val filterName: String,
    val filterThumbnail: String,
    val pictures: List<String>,
    val pureDegree: Int,
    val userProfile: String,
    val userName: String,
    val content: String
) {
    companion object {
        fun toUiModel(data: ReviewItem): ReviewHistoryUiModel {
            return ReviewHistoryUiModel(
                id = data.id,
                filterId = data.filterId,
                filterName = data.filterName,
                filterThumbnail = data.filterThumbnail,
                pureDegree = data.pureDegree,
                userProfile = data.userProfile,
                userName = data.userName,
                content = data.content,
                pictures = data.pictures ?: emptyList()
            )
        }
    }
}
