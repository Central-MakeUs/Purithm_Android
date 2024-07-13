package com.cmc.purithm.data.remote.dto

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?
)