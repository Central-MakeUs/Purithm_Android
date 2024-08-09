package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterReviewResponseDto (
    @SerializedName("avg")
    val avg : Int,
    val reviews : List<FilterReviewDto>
){
    data class FilterReviewDto(
        val id : Long,
        val pureDegree : Int,
        val user : String,
        val createdAt : String,
        val picture : List<String>
    )
}