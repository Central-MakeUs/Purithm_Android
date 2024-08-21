package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterLikeResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("membership")
    val membership: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("photographer")
    val photographerName: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("canAccess")
    val canAccess: Boolean
)
