package com.cmc.purithm.data.remote.dto.artist

import com.google.gson.annotations.SerializedName

internal data class ArtistResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("picture")
    val pictures: List<String>,
    @SerializedName("createdAt")
    val createdAt: String
)
