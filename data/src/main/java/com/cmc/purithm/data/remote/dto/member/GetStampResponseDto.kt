package com.cmc.purithm.data.remote.dto.member

import com.google.gson.annotations.SerializedName

internal data class GetStampResponseDto(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("list")
    val list: List<StampItemResponseDto>
) {
    internal data class StampItemResponseDto(
        @SerializedName("date")
        val date: String,
        @SerializedName("stamps")
        val stamps: List<StampItemDto>
    ) {
        internal data class StampItemDto(
            @SerializedName("filterId")
            val filterId: Long,
            @SerializedName("filterName")
            val filterName: String,
            @SerializedName("photographer")
            val photographer: String,
            @SerializedName("thumbnail")
            val thumbnail: String,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("membership")
            val membership: String,
            @SerializedName("reviewId")
            val reviewId: Long,
            @SerializedName("os")
            val os : String
        )
    }
}