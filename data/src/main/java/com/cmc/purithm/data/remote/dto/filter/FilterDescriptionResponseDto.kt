package com.cmc.purithm.data.remote.dto.filter

import com.cmc.purithm.domain.entity.filter.FilterIntroduction
import com.google.gson.annotations.SerializedName

internal data class FilterDescriptionResponseDto(
    @SerializedName("photographerId")
    val photographerId : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("profile")
    val profile : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("photoDescriptions")
    val photoDescriptions : List<FilterIntroduction>,
    @SerializedName("tag")
    val tag : List<String>
)