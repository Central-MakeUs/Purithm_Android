package com.cmc.purithm.feature.filter.model

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterImg

data class FilterDetailUiModel(
    val id: Long,
    val pureDegree: Int,
    val likes: Int,
    val liked: Boolean,
    val name: String,
    val pictures: List<FilterImg>
) {
    companion object {
        fun toUiModel(filter: Filter): FilterDetailUiModel {
            return FilterDetailUiModel(
                id = filter.id,
                pureDegree = filter.pureDegree,
                likes = filter.likes,
                liked = filter.liked,
                name = filter.name,
                pictures = filter.pictures
            )
        }
    }
}