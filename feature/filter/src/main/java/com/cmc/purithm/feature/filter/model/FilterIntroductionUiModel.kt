package com.cmc.purithm.feature.filter.model

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterIntroduction

data class FilterIntroductionUiModel(
    val photographerId: Long,
    val photographerProfile : String,
    val photographerName: String,
    val description: String,
    val photoDescriptions: List<FilterIntroduction>,
    val tag: List<String> = emptyList()
) {
    companion object {
        fun toUiModel(filter: Filter): FilterIntroductionUiModel {
            return FilterIntroductionUiModel(
                photographerId = filter.photographerId,
                photographerProfile = filter.photographerProfile,
                photographerName = filter.photographerName,
                description = filter.description,
                photoDescriptions = filter.filterIntroduction,
                tag = filter.tag
            )
        }
    }
}