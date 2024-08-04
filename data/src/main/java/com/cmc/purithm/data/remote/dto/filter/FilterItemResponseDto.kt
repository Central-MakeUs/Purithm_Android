package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterItemResponseDto(
    @SerializedName("isLast")
    val isLast : Boolean,
    @SerializedName("filters")
    val filters : List<FilterListDto>
)
