package com.cmc.purithm.feature.feed.model

import com.cmc.purithm.domain.entity.review.ReviewItem

data class FeedUiModel(
    val id: Long,
    val filterId: Long,
    val filterName: String,
    val filterThumbnail: String,
    val pictures: List<String>,
    val pureDegree: Int,
    val userProfile: String,
    val userName: String,
    val content: String,
    val canAccess : Boolean
) {
    companion object {
        fun toUiModel(data: ReviewItem): FeedUiModel {
            return FeedUiModel(
                id = data.id,
                filterId = data.filterId,
                filterName = data.filterName,
                filterThumbnail = data.filterThumbnail,
                pureDegree = data.pureDegree,
                userProfile = data.userProfile,
                userName = data.userName,
                content = data.content,
                pictures = data.pictures ?: emptyList(),
                canAccess = data.canAccess
            )
        }
    }
}
