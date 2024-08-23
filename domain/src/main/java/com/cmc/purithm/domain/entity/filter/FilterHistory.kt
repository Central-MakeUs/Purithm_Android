package com.cmc.purithm.domain.entity.filter

data class FilterHistory(
    val totalCount : Int,
    val list : List<FilterHistoryItem>
) {
    data class FilterHistoryItem(
        val date : String,
        val filters : List<Filter>
    )
}