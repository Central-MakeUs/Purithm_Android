package com.cmc.purithm.data.remote.dto.base

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)