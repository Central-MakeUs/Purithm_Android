package com.cmc.purithm.data.remote.dto.picture

import com.google.gson.annotations.SerializedName

internal data class GetUploadUrlResponseDto(
    @SerializedName("url")
    val url : String
)