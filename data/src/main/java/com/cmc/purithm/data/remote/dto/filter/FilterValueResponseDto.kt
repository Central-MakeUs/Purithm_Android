package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterValueResponseDto(
    @SerializedName("id")
    val id : Long,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("liked")
    val liked : Boolean,
    @SerializedName("value")
    val value : FilterValueDto
)
