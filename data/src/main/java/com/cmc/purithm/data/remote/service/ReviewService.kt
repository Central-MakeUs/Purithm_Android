package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.review.GetFilterReviewResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ReviewService {
    @GET("/api/filters/{filterId}/reviews")
    suspend fun getFilterReview(
        @Path("filterId") filterId: Long
    ): BaseResponse<GetFilterReviewResponseDto>
}