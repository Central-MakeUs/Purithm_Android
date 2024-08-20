package com.cmc.purithm.data.remote.dto.member

import com.google.gson.annotations.SerializedName

internal data class ProfileUpdateRequestDto(
    @SerializedName("name")
    val name : String,
    @SerializedName("profile")
    val profile : String
)
