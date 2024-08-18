package com.cmc.purithm.data.remote.dto.review

import com.google.gson.annotations.SerializedName

internal data class FilterReviewDto(
    @SerializedName("id")
    val id : Long,
    @SerializedName("profile")
    val profile : String?,
    @SerializedName("content")
    val content : String,
    @SerializedName("pureDegree")
    val pureDegree : Int,
    @SerializedName("user")
    val userName : String,
    @SerializedName("createdAt")
    val createAt : String,
    @SerializedName("pictures")
    val pictures : List<String>?
)
