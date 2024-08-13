package com.cmc.purithm.data.remote.dto.review

import com.google.gson.annotations.SerializedName

data class AddReviewRequestDto(
    @SerializedName("filterId")
    val filterId : Long,
    @SerializedName("pureDegree")
    val pureDegree : Int,
    @SerializedName("content")
    val content : String,
    @SerializedName("pictures")
    val pictures : List<String>
)
