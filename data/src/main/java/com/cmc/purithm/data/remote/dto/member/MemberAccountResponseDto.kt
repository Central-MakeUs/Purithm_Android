package com.cmc.purithm.data.remote.dto.member

import com.google.gson.annotations.SerializedName

internal data class MemberAccountResponseDto(
    @SerializedName("provider")
    val provider: String,
    @SerializedName("createdAt")
    val joinDate: String,
    @SerializedName("email")
    val email: String
)
