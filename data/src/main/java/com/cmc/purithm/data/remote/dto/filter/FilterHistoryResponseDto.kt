package com.cmc.purithm.data.remote.dto.filter

import com.google.gson.annotations.SerializedName

internal data class FilterHistoryResponseDto(
    @SerializedName("totalCount")
    val totalCount : Int,
    @SerializedName("list")
    val list : List<FilterHistoryItemResponseDto>
) {
    internal data class FilterHistoryItemResponseDto(
        @SerializedName("date")
        val date : String,
        @SerializedName("filters")
        val filters : List<FilterHistoryItemDto>
    ) {
        internal data class FilterHistoryItemDto(
            @SerializedName("filterId")
            val filterId : Long,
            @SerializedName("filterName")
            val filterName : String,
            @SerializedName("thumbnail")
            val thumbnail : String,
            @SerializedName("photographer")
            val photographer : String,
            @SerializedName("membership")
            val membership : String,
            @SerializedName("createdAt")
            val createdAt : String,
            @SerializedName("hasReview")
            val hasReview : Boolean,
            @SerializedName("reviewId")
            val reviewId : Long?,
            @SerializedName("os")
            val os : String
        )
    }
}