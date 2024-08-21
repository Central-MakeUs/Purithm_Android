package com.cmc.purithm.domain.entity.filter

data class FilterHistory(
    val totalCount : Int,
    val list : List<FilterHistoryItem>
) {
    data class FilterHistoryItem(
        val data : String,
        val filters : List<Filter>
    )
}