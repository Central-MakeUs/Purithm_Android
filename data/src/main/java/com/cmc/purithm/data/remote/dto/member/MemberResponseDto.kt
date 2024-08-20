package com.cmc.purithm.data.remote.dto.member

import com.google.gson.annotations.SerializedName

internal data class MemberResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("stamp")
    val stamp: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("filterViewCount")
    val filterViewCount: Int,
    @SerializedName("reviews")
    val reviews: Int

)