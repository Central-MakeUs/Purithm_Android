package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterReviewResponseDto (
    @SerializedName("avg")
    val avg : Int,
    @SerializedName("reviews")
    val reviews : List<FilterReviewDto>
){
    data class FilterReviewDto(
        @SerializedName("id")
        val id : Long,
        @SerializedName("pureDegree")
        val pureDegree : Int,
        @SerializedName("user")
        val user : String,
        @SerializedName("createdAt")
        val createdAt : String,
        @SerializedName("picture")
        val picture : List<String>
    )
}