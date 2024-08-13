package com.cmc.purithm.data.remote.dto.review

import com.google.gson.annotations.SerializedName

internal data class ReviewResponseDto(
    @SerializedName("content")
    val content : String,
    @SerializedName("username")
    val username : String,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("userProfile")
    val userProfile : String,
    @SerializedName("pictures")
    val pictures : List<String>,
    @SerializedName("pureDegree")
    val pureDegree : Int
)