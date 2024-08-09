package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterDetailResponseDto (
    @SerializedName("name")
    val name : String,
    @SerializedName("likes")
    val likes : Int,
    @SerializedName("pureDegree")
    val pureDegree : Int,
    @SerializedName("pictures")
    val pictures : List<FilterImgDto>,
    @SerializedName("liked")
    val liked : Boolean
) {
    data class FilterImgDto(
        @SerializedName("picture")
        val picture : String,
        @SerializedName("originalPicture")
        val originalPicture : String
    )
}