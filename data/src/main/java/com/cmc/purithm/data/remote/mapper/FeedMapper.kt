package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.feed.GetFeedResponseDto
import com.cmc.purithm.domain.entity.review.ReviewItem

internal fun BaseResponse<List<GetFeedResponseDto>>.toDomain(): List<ReviewItem> {
    val response = data ?: throw NullPointerException("data is null")
    return response.map {
        ReviewItem(
            id = it.id,
            filterName = it.filterName,
            filterId = it.filterId,
            filterThumbnail = it.filterThumbnail,
            pureDegree = it.pureDegree,
            userProfile = it.profile ?: "",
            content = it.content,
            userName = it.writer,
            pictures = it.pictures,
        )
    }
}