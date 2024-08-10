package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterValueDto(
    @SerializedName("lightBalance")
    val lightBalance : Int,
    @SerializedName("brightness")
    val brightness : Int,
    @SerializedName("exposure")
    val exposure : Int,
    @SerializedName("contrast")
    val contrast : Int,
    @SerializedName("highlight")
    val highlight : Int,
    @SerializedName("shadow")
    val shadow : Int,
    @SerializedName("saturation")
    val saturation : Int,
    @SerializedName("tint")
    val tint : Int,
    @SerializedName("temperature")
    val temperature : Int,
    @SerializedName("clear")
    val clear : Int,
    @SerializedName("clarity")
    val clarity : Int
)