package com.cmc.purithm.feature.filter.model

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterValue

data class FilterValueUiModel(
    val id : Long,
    val name : String,
    val liked : Boolean,
    val thumbnail : String,
    val filterValue : FilterValue
) {
    companion object {
        fun toUiModel(filter : Filter) = FilterValueUiModel(
            id = filter.id,
            name = filter.name,
            liked = filter.liked,
            thumbnail = filter.thumbnail,
            filterValue = filter.filterValue!!
        )
    }
}