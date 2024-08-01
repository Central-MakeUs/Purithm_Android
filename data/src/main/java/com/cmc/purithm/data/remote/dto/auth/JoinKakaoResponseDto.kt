package com.cmc.purithm.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class JoinKakaoResponseDto(
    @SerializedName("accessToken")
    val accessToken : String
)
