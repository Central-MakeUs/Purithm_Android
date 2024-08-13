package com.cmc.purithm.data.remote.dto.review

import com.google.gson.annotations.SerializedName

internal data class GetFilterReviewResponseDto(
    @SerializedName("avg")
    val avg: Int,
    @SerializedName("hasViewed")
    val hasViewed: Boolean,
    @SerializedName("reviews")
    val reviews: List<FilterReviewDto>
)
