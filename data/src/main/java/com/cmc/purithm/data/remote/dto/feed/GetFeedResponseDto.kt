package com.cmc.purithm.data.remote.dto.feed

import com.google.gson.annotations.SerializedName

internal data class GetFeedResponseDto(
    @SerializedName("id")
    val id : Long,
    @SerializedName("filterId")
    val filterId : Long,
    @SerializedName("filterName")
    val filterName : String,
    @SerializedName("filterThumbnail")
    val filterThumbnail : String,
    @SerializedName("writer")
    val writer : String,
    @SerializedName("profile")
    val profile : String?,
    @SerializedName("pureDegree")
    val pureDegree : Int,
    @SerializedName("content")
    val content : String,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("pictures")
    val pictures : List<String>,
    @SerializedName("canAccess")
    val canAccess : Boolean
)
