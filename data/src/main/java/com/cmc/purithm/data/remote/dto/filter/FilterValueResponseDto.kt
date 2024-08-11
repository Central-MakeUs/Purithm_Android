package com.cmc.purithm.data.remote.dto.filter

import com.cmc.purithm.domain.entity.filter.FilterValue
import com.google.gson.annotations.SerializedName

internal data class FilterValueResponseDto(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("liked")
    val liked : Boolean,
    @SerializedName("value")
    val filterValue : FilterValue
)
