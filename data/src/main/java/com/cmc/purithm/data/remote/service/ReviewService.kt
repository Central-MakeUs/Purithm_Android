package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.review.AddReviewRequestDto
import com.cmc.purithm.data.remote.dto.review.GetFilterReviewResponseDto
import com.cmc.purithm.data.remote.dto.review.ReviewResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface ReviewService {
    @GET("/api/filters/{filterId}/reviews")
    suspend fun getFilterReview(
        @Path("filterId") filterId: Long
    ): BaseResponse<GetFilterReviewResponseDto>

    @GET("/api/reviews/{reviewId}")
    suspend fun getReviewItem(
        @Path("reviewId") reviewId : Long
    ) : BaseResponse<ReviewResponseDto>

    @POST("/api/revies")
    suspend fun addReview(
        @Body addReviewRequestDto : AddReviewRequestDto
    ) : BaseResponse<Long>
}